package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.service.SecondLabService;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class SecondLabController {
    public Slider Phi;
    public Slider K;
    public LineChart<Double, Double> lineChart;
    public Button calc;
    private SecondLabService service = new SecondLabService();

    public void initialize() {
        service.setLineChart(lineChart);
    }

    public void calculate() {
        service.create(K.getValue(), Phi.getValue());
    }
}
