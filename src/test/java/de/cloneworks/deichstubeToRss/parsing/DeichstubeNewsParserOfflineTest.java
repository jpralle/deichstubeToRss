package de.cloneworks.deichstubeToRss.parsing;

import de.cloneworks.deichstubeToRss.exceptions.InvalidParameterException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DeichstubeNewsParserOfflineTest {

	@Test
	public void countNewsItemsInHtml() throws InvalidParameterException {

		ClassLoader classLoader = getClass().getClassLoader();
		File htmlFile = new File(classLoader.getResource("deichstube-html/DeichStube_news_20171218.html").getFile());

		DeichstubeNewsParser subject = new DeichstubeNewsParser(htmlFile, DeichstubeNewsParser.DEICHSTUBE_URL);

		assertThat(subject.getNumberOfItems(), is(9));
	}

	@Test
	public void readItemsFromHtml() throws InvalidParameterException {

		ClassLoader classLoader = getClass().getClassLoader();
		File htmlFile = new File(classLoader.getResource("deichstube-html/DeichStube_news_20171218.html").getFile());

		DeichstubeNewsParser subject = new DeichstubeNewsParser(htmlFile, DeichstubeNewsParser.DEICHSTUBE_URL);

		assertThat(subject.getNumberOfItems(), is(9));

		Iterator<NewsDataItem> itemIterator = subject.iterator();
		NewsDataItem item1 = itemIterator.next();
		assertThat(item1.title, is("Hinrunden-Abschluss weniger schlecht als befürchtet"));
		assertThat(item1.imageUrl, is("https://www.deichstube.de/bilder/2017/12/17/9456742/1180665021-florian-bruns-werder-bremen-2JlLzYZca7.jpg"));
		assertThat(item1.description, is("Bremen - Die Delegation, die am Morgen des 4. November an Gate A22 des Frankfurter Flughafens erschien, " +
				"war nicht gerade groß. Sechs, vielleicht sieben Männer, allesamt gekleidet in schwarzen Trainingsanzügen."));
		assertThat(item1.link, is("https://www.deichstube.de/news/ueber-winter-platz-16-werder-bremens-lage-nicht-ganz-so-schlimm-9456742.html"));

		NewsDataItem item2 = itemIterator.next();
		assertThat(item2.title, is("Das passiert am Montag"));
		assertThat(item2.imageUrl, is("https://www.deichstube.de/bilder/2017/08/17/9456688/16215135-dailydeich_stadion_teaser_werder-tag_201117-Ha7.jpg"));
		assertThat(item2.description, is("Was passiert heute bei Werder Bremen? Lest hier unseren täglichen Überblick - den DailyDeich - über " +
				"Termine, Personal und das Wichtigste vom Tage. Jeden Morgen ab 5 Uhr."));
		assertThat(item2.link, is("https://www.deichstube.de/news/werder-bremen-termine-18-dezember-2017-dailydeich-9456688.html"));

		NewsDataItem item3 = itemIterator.next();
		assertThat(item3.title, is("Taktik-Analyse: Werder zerstört starken Auftritt in wenigen Sekunden"));
		assertThat(item3.imageUrl, is("https://www.deichstube.de/bilder/2017/12/17/9456294/620438558-florian-kohfeldt-werder-bremen-ZFPPv2ya7.jpg"));
		assertThat(item3.description, is("Bremen - Über nahezu neunzig Minuten hatte Werder den FSV Mainz 05 im Griff. Vor allem defensiv agierten " +
				"die Bremer clever. Unser Taktikexperte Tobias Escher ist etwas ratlos, warum am Ende nur ein Punkt dabei heraussprang."));
		assertThat(item3.link, is("https://www.deichstube.de/spieltag/werder-bremen-mainz-05-1718-ere798275/taktik-analyse-werder-bremen-zerstoert-" +
				"einen-taktisch-starken-auftritt-in-wenigen-sekunden-9456294.html"));

		NewsDataItem item4 = itemIterator.next();
		assertThat(item4.title, is("Netzreaktionen: „Vor dem HSV. Vor dem HSV.“"));
		assertThat(item4.imageUrl, is("https://www.deichstube.de/bilder/2017/12/16/9456230/745218449-teaser-twitter-20170327114918-BHU8ZT5I4a7.jpg"));
		assertThat(item4.description, is("Einen Super-Start hingelegt, am Ende aber nur einen Punkt im Abstiegskampf geholt. So reagierte Twitter " +
				"nach Werders 2:2 gegen den FSV Mainz 05."));
		assertThat(item4.link, is("https://www.deichstube.de/spieltag/werder-bremen-mainz-05-1718-ere798275/werder-bremen-netzreaktionen-" +
				"unentschieden-gegen-fsv-mainz-05-9456230.html"));

		NewsDataItem item5 = itemIterator.next();
		assertThat(item5.title, is("Belfodil ist stolz auf sich"));
		assertThat(item5.imageUrl, is("https://www.deichstube.de/bilder/2017/12/16/9456080/1268222205-ishak-belfoldil-werder-bremen-1Yamfj7Va7.jpg"));
		assertThat(item5.description, is("Bremen - Erstes Tor in der Bundesliga, dazu seine bisher beste Leistung im Werder-Trikot: Für Ishak " +
				"Belfodil war der Samstagnachmittag im Bremer Weserstadion eigentlich ein rundum gelungener - wenn da nur dieses blöde Ergebnis " +
				"nicht gewesen wäre."));
		assertThat(item5.link, is("https://www.deichstube.de/news/werder-bremen-ishak-belfodil-stolz-sich-9456080.html"));

		NewsDataItem item6 = itemIterator.next();
		assertThat(item6.title, is("Das passiert am Sonntag"));
		assertThat(item6.imageUrl, is("https://www.deichstube.de/bilder/2017/08/17/9456066/16215135-dailydeich_stadion_teaser_werder-tag_201117-Ha7.jpg"));
		assertThat(item6.description, is("Was passiert heute bei Werder Bremen? Lest hier unseren täglichen Überblick - den DailyDeich - über " +
				"Termine, Personal und das Wichtigste vom Tage. Jeden Morgen ab 5 Uhr."));
		assertThat(item6.link, is("https://www.deichstube.de/news/werder-bremen-termine-17-dezember-2017-dailydeich-9456066.html"));

		NewsDataItem item7 = itemIterator.next();
		assertThat(item7.title, is("Einzelkritik: Mit Bargfrede ist Werder besser"));
		assertThat(item7.imageUrl, is("https://www.deichstube.de/bilder/2017/12/16/9456006/536764149-werder-bremen-philipp-bargfrede-1bjppSPa7.jpg"));
		assertThat(item7.description, is("Die Partie gegen Mainz 05 fing trotz der Verletzungen von Junuzovic und Kruse vielversprechend an, zum " +
				"Schluss wurde es bitter, als Werder die 2:0-Führung dann doch aus der Hand gab. Mit dem 2:2-Endstand überwintert das Team auf dem …"));
		assertThat(item7.link, is("https://www.deichstube.de/spieltag/werder-bremen-mainz-05-1718-ere798275/werder-bremen-einzelkritik-22-" +
				"unentschieden-gegen-mainz-9456006.html"));

		NewsDataItem item8 = itemIterator.next();
		assertThat(item8.title, is("Nachlässig in der Nachspielzeit"));
		assertThat(item8.imageUrl, is("https://www.deichstube.de/bilder/2017/12/16/9456055/1527707323-ishak-belfodil-werder-bremen-oaFgUhw4ca7.jpg"));
		assertThat(item8.description, is("Bremen - Im Grunde genügte am Samstagabend schon ein einziger Satz, und die ganze Enttäuschung von " +
				"Florian Kohfeldt war im Presseraum des Bremer Weserstadions greifbar."));
		assertThat(item8.link, is("https://www.deichstube.de/spieltag/werder-bremen-mainz-05-1718-ere798275/nachlaessig-in-nachspielzeit-nachbericht-" +
				"werder-bremen-gegen-fsv-mainz-05-9456055.html"));

		NewsDataItem item9 = itemIterator.next();
		assertThat(item9.title, is("Sorgen auch um Bargfrede"));
		assertThat(item9.imageUrl, is("https://www.deichstube.de/bilder/2017/12/16/9456045/1244208693-philipp-bargfrede-werder-bremen-aDNbbwtHaa7.jpg"));
		assertThat(item9.description, is("Bremen - Florian Kohfeldt hatte die Hoffnung am Freitag noch mit ins Bett genommen und war am Samstag " +
				"auch mit ihr aufgestanden. Doch als der Anpfiff des Spiels gegen den FSV Mainz 05 immer näher rückte, wurde klar, dass es " +
				"weder für Max Kruse noch …"));
		assertThat(item9.link, is("https://www.deichstube.de/news/sorgen-auch-philipp-bargfrede-nach-spiel-werder-bremen-gegen-mainz-05-9456045.html"));

	}

}
