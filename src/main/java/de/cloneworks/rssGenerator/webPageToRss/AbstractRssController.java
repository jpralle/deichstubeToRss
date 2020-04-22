package de.cloneworks.rssGenerator.webPageToRss;

import com.rometools.rome.io.FeedException;
import com.sun.mail.smtp.SMTPTransport;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.RssDataItem;
import de.cloneworks.rssGenerator.webPageToRss.webPageParsing.WebParserForRss;
import de.cloneworks.rssGenerator.webPageToRss.rssGeneration.RssGenerator;
import de.cloneworks.rssGenerator.webPageToRss.util.InvalidParameterException;
import de.cloneworks.rssGenerator.webPageToRss.util.MyLogger;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

abstract public class AbstractRssController {

	private static final MyLogger LOGGER = new MyLogger(AbstractRssController.class);

	@Value("${sendMail}")
	private boolean sendMail;

	private static Map<String, String> lastRssMap = new HashMap<>();
	private static Map<String, Date> lastRssGenerationTimeMap = new HashMap<>();

	protected String rssFor(HttpServletRequest request, RssDataConfiguration config) {

		try {

			return innerRssFor(request, config);

		} catch (Throwable t) {
			if(sendMail) {
				sendMail(t);
			}
			throw t;
		}
	}

	private String innerRssFor(HttpServletRequest request, RssDataConfiguration config) {
		String ret = "error";

		if(cacheIsStillValid(config.getLocalBaseUrl())) {
			return lastRssMap.get(config.getLocalBaseUrl());
		}

		try {

			WebParserForRss parser = new WebParserForRss(config);
			parser.setBaseUrl(request.getLocalAddr() + config.getLocalBaseUrl());
			StringBuilder sb = new StringBuilder(parser.getNumberOfItems() + " items haven been found: \n");

			for(RssDataItem item : parser) {
				sb.append(item);
				sb.append("\n");
			}

			LOGGER.debug(sb.toString());

			ret = RssGenerator.generateFrom(parser, config);

			saveRss(config.getLocalBaseUrl(), ret);

		} catch (InvalidParameterException e) {
			LOGGER.error("Error creating RSS. Reason: " + e, e);
		} catch (FeedException e) {
			LOGGER.error("Error writing RSS to String. Reason: " + e, e);
		}

		return ret;
	}

	private boolean cacheIsStillValid(String cacheKey) {
		boolean ret = false;

		Date lastRssGenerationTime = lastRssGenerationTimeMap.get(cacheKey);
		if(lastRssGenerationTime != null) {
			Date now = Calendar.getInstance().getTime();
			long diffInMillis = now.getTime() - lastRssGenerationTime.getTime();

			if(diffInMillis >= 0 && diffInMillis < 3600000) {
				ret = true;
				DateFormat dateFormat = new SimpleDateFormat();
				LOGGER.info("Using cache from " + dateFormat.format(lastRssGenerationTime));
			}
		}

		return ret;
	}

	private void saveRss(String cacheKey, String rss) {
		lastRssGenerationTimeMap.put(cacheKey, Calendar.getInstance().getTime());
		lastRssMap.put(cacheKey, rss);
		LOGGER.info("Generated new cache for \"" + cacheKey + "\".");
	}

	private void sendMail(Throwable t) {

		String smtpServer = "smtp.example.com";

		Properties systemProperties = System.getProperties();
		systemProperties.put("mail.smtp.host", smtpServer); //optional, defined in SMTPTransport
		systemProperties.put("mail.smtp.auth", "true");
		systemProperties.put("mail.smtp.port", "25"); // default port 25

		Session session = Session.getInstance(systemProperties, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress("deichstubeToRss@cloneworks.de"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jpralle@cloneworks.de", false));
			msg.setSubject("Fehler in DeichstubeToRss-Anwendung: " + t.getMessage());
			msg.setText(getMailTextFromThrowable(t));
			msg.setSentDate(new Date());

			SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
			transport.connect(smtpServer, "user", "password");
			transport.sendMessage(msg, msg.getAllRecipients());

			LOGGER.warn("Response from sending error mail: " + transport.getLastServerResponse());

			transport.close();

		} catch (MessagingException e) {
			LOGGER.warn("Error while sending error mail: " + e.getMessage(), e);
		}

	}

	private String getMailTextFromThrowable(Throwable t) {
		String text = "Da lief was beim konvertieren des Fehlers in den Mailtext schief :(";

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final String utf8 = StandardCharsets.UTF_8.name();

		try (PrintStream ps = new PrintStream(baos, true, utf8)) {
			t.printStackTrace(ps);
			text = baos.toString(utf8);

		} catch (UnsupportedEncodingException ex) {
			LOGGER.warn("Problem while converting throwable to mail text: " + ex.getMessage(), ex);
		}

		return text;
	}

}
