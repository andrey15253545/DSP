package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.model.Spectra;
import bsuir.DSP.lab.service.ThirdLabService;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

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
    private Spectra spectra;

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
        showSignalWithFourier(listSignals);
    }

    public void drawPolyHarmonicChart() {
        polyHarmonicPane.setDisable(false);
        List<Double> listSignals = service.createPolyHarmonicSignal();
        showSignalWithFourier(listSignals);
    }

    private void showSignalWithFourier(List<Double> listSignals) {
        signalChart.getData().clear();
        spectraChart.getData().clear();
        drawInSignalChart(service.createSeries(listSignals, "signal"));
        spectra = service.createSpectra(listSignals);
        drawInSpectraChart(service.createSeries(spectra.getAmplitude(), "Amplitude"));
        drawInSpectraChart(service.createSeries(spectra.getPhase(), "Phase"));
        List<Double> restoreSignal = service.restoreSignal(spectra);
        drawInSignalChart(service.createSeries(restoreSignal, "restored signal"));
    }

    public void createLowFilter() {
        spectraChart.getData().clear();
        drawInSpectraChart(service.createSeries(service.restoreLow(spectra, 10.0), "low signal"));
    }

    public void createHighSignal() {
        spectraChart.getData().clear();
        drawInSpectraChart(service.createSeries(service.restoreHigh(spectra, 15.0), "high signal"));
    }

    public void createBandPathSignal() {
        spectraChart.getData().clear();
        drawInSpectraChart(service.createSeries(service.restoreBandPath(spectra, List.of(0, 5, 10, 15)), "Band Path Signal"));
    }

    private void drawInSignalChart(XYChart.Series<Double, Double> series) {
        signalChart.getData().add(series);
    }

    private void drawInSpectraChart(XYChart.Series<Double, Double> series) {
        spectraChart.getData().add(series);
    }


}
