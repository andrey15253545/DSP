package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.model.Signal;
import bsuir.DSP.lab.service.FirstLabService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstLabController {

    private static final FirstLabService service = new FirstLabService();
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
    public RadioButton sinusoid;
    public RadioButton dutyCycle;
    public RadioButton triangle;
    public RadioButton saw;
    public RadioButton noise;
    public Button sound;
    private XYChart.Series series = new XYChart.Series<Double, Double>();
    private boolean isPolyHarm = false;


    public void initialize() {
        ToggleGroup group = new ToggleGroup();
        sinusoid.setToggleGroup(group);
        dutyCycle.setToggleGroup(group);
        triangle.setToggleGroup(group);
        saw.setToggleGroup(group);
        noise.setToggleGroup(group);
        createSignal();
        harmSliderA.valueProperty().addListener((ch,old,New)-> createSignal());
        harmSliderFi.valueProperty().addListener((ch,old,New)-> createSignal());
        harmSliderF.valueProperty().addListener((ch,old,New)-> createSignal());
        polyHarmPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> createSignal());
        polyHarmPane.setDisable(true);
        service.setSeries(series);
        LineChart.getData().add(series);
    }

    public void harmonicDrag() {
        createSignal();
    }

    private void createSignal(){
        service.createSignal(getPolyHarmSignal());
    }

    public void click() {
        isPolyHarm = !isPolyHarm;
        polyHarmPane.setDisable(isPolyHarm);
        harmonicPane.setDisable(!isPolyHarm);
    }

    public void showSinusoid() {
        service.createSinusoid(getPolyHarmSignal());
    }

    public void dutyCycle() {
        service.createDutyCycle(getPolyHarmSignal());
    }

    public void triangle() {
        service.createTriangle(getPolyHarmSignal());
    }

    public void saw() {
        service.createSaw(getPolyHarmSignal());
    }

    public void noise() {
        service.createNoise(getPolyHarmSignal());
    }

    public void playSound() {
        service.playSignal(getPolyHarmSignal());
    }

    private List<Signal> getPolyHarmSignal() {
        if (isPolyHarm)
            return new ArrayList<>(Collections.singletonList(new Signal(harmSliderA.getValue(), harmSliderF.getValue(), harmSliderFi.getValue())));
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

    public void frequencyModulation() {
        service.createFrequencyModulation(getPolyHarmSignal());
    }

    public void amplitudeModulation() {
        service.createAmplitudeModulation(getPolyHarmSignal());
    }
}
