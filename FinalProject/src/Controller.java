/*
Steven Prine
Prof. Noynaert
CSC-346
 */

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


public class Controller {

    //Getting the url from the creditials xml file through the credentials class
    private String url = Credentials.getUrl();
    private String logoUrl;

    //Setting the textfield input to a variable.
    @FXML
    private TextField input;

    //output is going to hold the output of the program in text.
    @FXML
    private TextArea output;

    //Rectangle will be for setting up an image.
    @FXML
    public ImageView image;

    //Anime value
    @FXML
    private TextArea anime;

    @FXML
    public void initialize() {
        System.out.println("The controller has been initialized");
        anime.setEditable(false);
        anime.setWrapText(true);
        anime.setFont(Font.font(18));

        output.setEditable(false);
        output.setWrapText(true);
        output.setFont(Font.font(18));
    }

    //Creating the event handler for when the button is pressed.
    public void submitAction(ActionEvent event) {
        String name = input.getText().trim().replaceAll(" ", "+");

        String xmlString = readUrl(url + name);
        Document doc = Jsoup.parse(xmlString, url, Parser.xmlParser());
        String error = doc.select("warning").text();

        if (name.length() == 0) {
            System.out.println("Please Enter A Title");
            anime.setText("Please Enter A Title");

        } else if (error.contains("no results")) {
            System.out.println(" I M ADE IT HERE");
            anime.setText("No Results Found");

        } else {

//Anime Information
            String englishTitle = doc.select("anime").attr("name");
            String animeType = doc.select("anime").attr("type");
            String plotSummary = doc.select("anime info[type=Plot Summary]").text();
            String episodesCount = doc.select("anime info[type=Number of episodes]").text();

            //format for summary output
            String animeFormatter = String.format("%s\n%s\nEpisodes For This Show:\n%s \n\n%s"
                    , englishTitle, animeType, episodesCount, plotSummary);
            anime.setText(animeFormatter);
            output.setText("");

//Episode Information
            Elements episodes = doc.select("episode");

            String ep = null;
            for (Element episode : episodes) {
                ep = episode.select("title").first().text();
                output.appendText(ep + "\n");
            }

//Image Information Will be Done Here

            //Creating the image on a shape I can move around.
            logoUrl = doc.select("anime info[src]").first().attr("src").replaceAll(" ", "");
            String modUrl = "https" + logoUrl.substring(4);
            System.out.println(modUrl);
            Image tempImage = new Image(modUrl);
            image.setImage(tempImage);

        }
    }

    //Hyperlink Event Listener
    public void hyperlink(ActionEvent event) {

        try {
            Desktop.getDesktop().browse(new URL("https://www.animenewsnetwork.com/encyclopedia/").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }



//    readUrl is used for reading in the url to be parsed.
    private static String readUrl(String urlString) {
        String data = "";

        try {
            URL url = new URL(urlString);

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String s;
            while ((s = input.readLine()) != null) {
                data += s;
            }

            input.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return data;
    }
}