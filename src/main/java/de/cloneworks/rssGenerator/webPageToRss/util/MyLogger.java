package de.cloneworks.rssGenerator.webPageToRss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Configuration
//@PropertySource("classpath:application.properties")
public class MyLogger {

//    @Value("${sendMail}")
//    private String sendMail;

    public static final int MAIL_SERVER_STARTTLS_PORT = 587;

    public static final String MAIL_USER = "deichstubetorss@cloneworks.de";
    public static final String MAIL_PASSWORD = "h2rxXW9tdnbddfsY";
    private final Logger LOGGER;

//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public MyLogger(Class clazz) {
        this.LOGGER = LoggerFactory.getLogger(clazz);
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    public void error(String message, Throwable t) {
//        sendMail(message, t);
        LOGGER.error(message, t);
    }

    public void error(String message) {
//        sendMail(message);
        LOGGER.error(message);
    }

    public void warn(String message, Throwable t) {
        LOGGER.warn(message, t);
    }

    public void warn(String message) {
        LOGGER.warn(message);
    }

    public void info(String message, Throwable t) {
        LOGGER.info(message, t);
    }

    public void info(String message) {
        LOGGER.info(message);
    }

    public void debug(String message, Throwable t) {
        LOGGER.debug(message, t);
    }

    public void debug(String message) {
        LOGGER.debug(message);
    }

//    private void sendMail(String errorMessage) {
//        sendMail(errorMessage, null);
//    }
//
//    private void sendMail(String errorMessage, Throwable t) {
//
//        if(!sendMail.equalsIgnoreCase("true")) {
//            return;
//        }
//
//        LOGGER.info("Sending mail for error \"" + errorMessage + "\"...");
//
//        String exceptionMessage = "no exception thrown";
//        String exceptionStackTrace = "no exception thrown";
//
//        if(t != null) {
//            exceptionMessage = t.getMessage();
//
//            StringWriter stringWriter = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(stringWriter);
//            t.printStackTrace(printWriter);
//            exceptionStackTrace = stringWriter.toString();
//        }
//
//        String to = "deichstubeToRssErrors@cloneworks.de";
//        String from = "deichstubeNews@cloneworks.de";
//
//        String host = "v075548.kasserver.com";
//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.put("mail.smtp.port", MAIL_SERVER_STARTTLS_PORT);
//        properties.put("mail.smtp.auth", true);
//        properties.put("mail.smtp.socketFactory.port", MAIL_SERVER_STARTTLS_PORT);
//        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(MAIL_USER, MAIL_PASSWORD);
//            }
//
//        });
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject(errorMessage);
//            message.setText(errorMessage + "\n\nCause: " + exceptionMessage + "\n\nStacktrace:\n" + exceptionStackTrace);
//
//            Transport.send(message);
//
//            LOGGER.info("Sent mail for error \"" + errorMessage + "\" successfully.");
//        } catch (MessagingException ex) {
//            LOGGER.warn("Unable to send mail for error \"" + errorMessage + "\". This ist not good. Admin is not informed.", ex);
//        }
//    }
}
