/*
Steven Prine
CSC-346
Prof. Noynaert
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Credentials {
    private static String url = "";
    private static String apiKey = "k";

    private static boolean populate() {
        boolean success = false;
        if (url.length() < 2) {
            File inputFile = new File("credentials.xml");
            String xml = "";

            //read the file into a single string
            try {
                Scanner input = new Scanner(inputFile);

                while (input.hasNextLine()) {
                    xml += input.nextLine();
                }

                input.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }

            // PROCESS THE URL
            //This uses the XMLParser which does not assume html tags.
            Document doc = Jsoup.parse(xml, url, Parser.xmlParser());

           //get The URL
            Elements urlNodes = doc.select("url").after("port=");

            //There should be only one url node
            Element urlNode = urlNodes.first();
            url = urlNode.text();

            //Get the table name, this time demonstrating one line instead of 3
            apiKey = doc.select("weatherUnderground").first().text();

        }
        return success;
    }

    public static String getUrl() {
        populate();
        return url;
    }

    public static String getApiKey() {
        populate();
        return apiKey;
    }

}