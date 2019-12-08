package bsuir.DSP.lab.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public Button button1;
    public Button button2;

    public void openFirstLab() {
        String fxmlFile = "/fxml/first.fxml";
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle("First");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSecondLab() {
        String fxmlFile = "/fxml/second.fxml";
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle("First");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
