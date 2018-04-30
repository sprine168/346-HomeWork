/*
Steven Prine
Prof. Noynaert
CSC-346
This program is designed to read in from a weather api and take input for the current weather for
the state and city inputted.weatherGui
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class weatherMain extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("weatherApp.fxml"));
        primaryStage.setTitle("Weather Application");
        primaryStage.setScene(new Scene(root, 900, 575));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




