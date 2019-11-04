package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.model.Signal;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirstLabController {

    private static final int N = 512;
    public Slider harmSliderA;
    public Slider harmSliderF;
    public Slider harmSliderFi;
    public Slider polyHarmSliderA1;
    public Slider polyHarmSliderF;
    public Slider polyHarmSliderPhi1;
    public Slider polyHarmSliderA2;
    public Slider polyHarmSliderF2;
    public Slider polyHarmSliderPhi2;
    public Slider polyHarmSliderA3;
    public Slider polyHarmSliderF3;
    public Slider polyHarmSliderPhi3;
    public Slider polyHarmSliderA4;
    public Slider polyHarmSliderF4;
    public Slider polyHarmSliderPhi4;
    public Slider polyHarmSliderA5;
    public Slider polyHarmSliderF5;
    public Slider polyHarmSliderPhi5;
    public Pane polyHarmPane;
    public LineChart<Double, Double> LineChart;
    public RadioButton RadioButton;
    public Pane harmonicPane;
    public Button button;
    private XYChart.Series series = new XYChart.Series<Double, Double>();
    private boolean isPolyHarm = false;

    public void initialize() {
        createHarmonicSignal();
        harmSliderA.valueProperty().addListener((ch,old,New)-> createHarmonicSignal());
        harmSliderFi.valueProperty().addListener((ch,old,New)-> createHarmonicSignal());
        harmSliderF.valueProperty().addListener((ch,old,New)-> createHarmonicSignal());
        polyHarmPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> createPolyHarmSignal());
        polyHarmPane.setDisable(true);
        LineChart.getData().add(series);
    }

    public void harmonicDrag(MouseEvent mouseEvent) {
        createHarmonicSignal();
    }

    private void createHarmonicSignal(){
        series.getData().clear();
        for (int x = 0; x < N; x++) {
            double y = harmSliderA.getValue() * Math.sin( 2 * Math.PI * harmSliderF.getValue() * x / N + harmSliderFi.getValue() / 180 * Math.PI);
            series.getData().add(new XYChart.Data<>(x, y));
        }
    }

    private void createPolyHarmSignal() {
        series.getData().clear();
        List<Signal> list = getPolyHarmSignal();
        for (int i = 0; i<N; i++) {
            double res = 0;
            for (Signal signal : list) {
                res += signal.getA() + Math.sin(2 * Math.PI * signal.getF() * i / N + signal.getPhi() / 180 * Math.PI);
            }
            series.getData().add(new XYChart.Data<>(i, res));
        }
    }

    private List<Signal> getPolyHarmSignal() {
        List<Signal> list = new ArrayList<>();
        Signal signal1 = new Signal(polyHarmSliderA1.getValue(), polyHarmSliderF.getValue(), polyHarmSliderPhi1.getValue());
        Signal signal2 = new Signal(polyHarmSliderA2.getValue(), polyHarmSliderF2.getValue(), polyHarmSliderPhi2.getValue());
        Signal signal3 = new Signal(polyHarmSliderA3.getValue(), polyHarmSliderF3.getValue(), polyHarmSliderPhi3.getValue());
        Signal signal4 = new Signal(polyHarmSliderA4.getValue(), polyHarmSliderF4.getValue(), polyHarmSliderPhi4.getValue());
        Signal signal5 = new Signal(polyHarmSliderA5.getValue(), polyHarmSliderF5.getValue(), polyHarmSliderPhi5.getValue());
        list.add(signal1);
        list.add(signal2);
        list.add(signal3);
        list.add(signal4);
        list.add(signal5);
        return list;
    }

    public void click(ActionEvent actionEvent) {
        isPolyHarm = !isPolyHarm;
        polyHarmPane.setDisable(isPolyHarm);
        harmonicPane.setDisable(!isPolyHarm);
    }


    public void clickButton(ActionEvent actionEvent) {

        Stage stage = new Stage();
        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream(fxmlFile));
            stage.setTitle("JavaFX and Maven");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
