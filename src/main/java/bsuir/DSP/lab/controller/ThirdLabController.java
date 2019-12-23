package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.service.ThirdLabService;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ThirdLabController {

    private static final ThirdLabService service = new ThirdLabService();

    public LineChart spectraChart;
    public LineChart signalChart;
    public Button harmButton;
    public Button polyharmButton;


    public void drawChart() {
        List<Double> listSignals = service.createHarmSignal();
        signalChart.getData().add(service.createSeries(listSignals, "signal"));
        Spectra spectra = service.createSpectra(listSignals);
        spectraChart.getData().add(service.createSeries(spectra.amplitude, "Amplitude"));
        spectraChart.getData().add(service.createSeries(spectra.phase, "Phase"));
        List<Double> restoreSignal = service.restoreSignal(spectra);
        signalChart.getData().add(service.createSeries(restoreSignal, "restored signal"));
    }


    @Data
    public static class Spectra {
        List<Double> amplitude = new ArrayList<>();
        List<Double> phase = new ArrayList<>();
    }

}
