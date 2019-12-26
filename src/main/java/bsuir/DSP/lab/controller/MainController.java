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
    public Button button3;
    public Button button4;

    public void openFirstLab() {
        openFile("/fxml/first.fxml", "First lab");
    }

    public void openSecondLab() {
        openFile("/fxml/second.fxml", "second lab");
    }

    private void openFile(String url, String name) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResourceAsStream(url));
            Stage stage = new Stage();
            stage.setTitle(name);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openThirdLab() {
        openFile("/fxml/third.fxml", "third lab");
    }

    public void openFourthLab() {
        openFile("/fxml/fourth.fxml", "fourth lab");
    }
}
