package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.model.Spectra;
import bsuir.DSP.lab.service.ThirdLabService;
import javafx.scene.chart.LineChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import java.util.List;

public class ThirdLabController {

    private static final ThirdLabService service = new ThirdLabService();

    public LineChart spectraChart;
    public LineChart signalChart;
    public RadioButton harmButton;
    public RadioButton polyHarmButton;
    public RadioButton lowFilter;
    public RadioButton highFilter;
    public RadioButton bandPathFilter;
    public Pane polyHarmonicPane;

    public void initialize() {
        polyHarmonicPane.setDisable(true);
        ToggleGroup group = new ToggleGroup();
        harmButton.setToggleGroup(group);
        polyHarmButton.setToggleGroup(group);
        ToggleGroup polyHarmGroup = new ToggleGroup();
        lowFilter.setToggleGroup(polyHarmGroup);
        highFilter.setToggleGroup(polyHarmGroup);
        bandPathFilter.setToggleGroup(polyHarmGroup);
    }

    public void drawChart() {
        polyHarmonicPane.setDisable(true);
        List<Double> listSignals = service.createHarmSignal();
        signalChart.getData().add(service.createSeries(listSignals, "signal"));
        Spectra spectra = service.createSpectra(listSignals);
        spectraChart.getData().add(service.createSeries(spectra.getAmplitude(), "Amplitude"));
        spectraChart.getData().add(service.createSeries(spectra.getPhase(), "Phase"));
        List<Double> restoreSignal = service.restoreSignal(spectra);
        signalChart.getData().add(service.createSeries(restoreSignal, "restored signal"));
    }

    public void drawPolyHarmonicChart() {
        polyHarmonicPane.setDisable(false);
        List<Double> listSignals = service.createPolyHarmonicSignal();
    }

    public void createLowFilter() {

    }

    public void createHighSignal() {
    }

    public void createBandPathSignal() {
    }
}
