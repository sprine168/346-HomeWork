/*
Steven Prine
Prof. Noynaert
CSC-346
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class weatherController {
    private String url = Credentials.getUrl();
    private String api = Credentials.getApiKey();
    private String logoUrl;

    @FXML
    private TextField state;

    @FXML
    private TextField city;

    @FXML
    private Button submit;

    @FXML
    private TextArea output;

    @FXML
    private Rectangle image;

    @FXML
    public void initialize() {
        System.out.println("The weather Controller has Been Initialized");
        output.setEditable(false);
    }

    public weatherController() {
        System.out.println("The constructor is working well at the moment");
    }

    public void submitAction(ActionEvent event) {
        String cityString = city.getText();
        String stateString = state.getText();

        if (cityString.length() > 0 && stateString.length() == 2) {

            stateString = stateString.toUpperCase().substring(0, 2).toLowerCase().trim();
            cityString = cityString.replaceAll(" ", "_").toLowerCase().trim();

            String xmlString = readUrl(url + api + "/conditions/q/" + stateString + "/" + cityString + ".xml");
            Document doc = Jsoup.parse(xmlString, url, Parser.xmlParser());

            String error = doc.select("type").text();
            if (error.equals("querynotfound") || error.equals("invalidformat")) {
                System.out.println("\nPlease enter a valid city and state");
                output.setText("Please Enter A Valid City and State Abbreviate");
            }

            String weather = doc.select("weather").text();
            String tempf = doc.select("temp_f").text();
            String tempc = doc.select("temp_c").text();
            String wind = doc.select("wind_string").text();

            logoUrl = doc.select("url").text();
            Image tempImage = new Image(logoUrl);
            image.setFill(new ImagePattern(tempImage));

            System.out.printf(
                    "The weather is: %s\n" +
                            "The temperature in faranheit is: %s\n" +
                            "The temperature in Ceclius is: %s\n" +
                            "The wind is %s\n", weather, tempf, tempc, wind);

            String formatter = String.format("The city is: %s\nThe State is: %s\n" +
                    "The weather is: %s\n" +
                    "The temperature in faranheit is: %s\n" +
                    "The temperature in Ceclius is: %s\n" +
                    "The wind is %s\n", cityString, stateString, weather, tempf, tempc, wind);

            output.setText(formatter);

        } else {
            System.out.println("Please Enter a State Abbreviation and a Valid City");
            output.setText("Please Enter a State Abbreviation and a Valid City");
        }
    }

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