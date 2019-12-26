package bsuir.DSP.lab.service;

import bsuir.DSP.lab.model.Signal;
import bsuir.DSP.lab.model.Spectra;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class ThirdLabService {

    private static final int N = 512;
    private static final int K = 31;
    private static final List<Double> AMPLITUDES = List.of(1.0, 5.0, 7.0, 8.0, 9.0, 10.0, 17.0);
    private static final List<Double> PHASES = List.of(Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2, 3 * Math.PI / 4, Math.PI);

    public XYChart.Series<Double, Double> createSeries(List<Double> list) {
        return createSeries(list, null);
    }

    public XYChart.Series<Double, Double> createSeries(List<Double> list, String name) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.setName(name);
        for (int i=0; i<list.size(); i++) {
            series.getData().add(new XYChart.Data<>((double)i, list.get(i)));
        }
        return series;
    }

    public List<Double> createHarmSignal() {
        List<Double> list = new ArrayList<>();
        for (int i=0; i<N; i++) {
            list.add(50 * Math.cos(2 * Math.PI * i / N - Math.PI / 3));
        }
        return list;
    }

    public Spectra createSpectra(List<Double> signal) {
        Spectra result = new Spectra();
        for (int i=0; i<N/2; i++) {
            double amplitudeS = 0;
            double amplitudeC = 0;
            for (int j = 0; j < N; j++) {
                double w = 2 * Math.PI * i * j / N;

                amplitudeS += signal.get(j) * Math.sin(w);
                amplitudeC += signal.get(j) * Math.cos(w);
            }

            amplitudeS *= 2.0 / N;
            amplitudeC *= 2.0 / N;

            result.getPhase().add(Math.atan2(amplitudeS, amplitudeC));
            result.getAmplitude().add(Math.hypot(amplitudeS, amplitudeC));
        }
        return result;
    }

    public List<Double> restoreSignal(Spectra spectra) {
        List<Double> restoreSignal = new ArrayList<>();
        for (int i=0; i<N; i++) {
            double sum = 0;
            for (int j = 0; j < spectra.getAmplitude().size() / 2; j++) {
                sum += spectra.getAmplitude().get(j) * Math.cos(2 * Math.PI * j * i / N - spectra.getPhase().get(j));
            }
            restoreSignal.add(sum);
        }
        return restoreSignal;
    }

    public List<Double> restoreBandPath(Spectra spectra, List<Integer> ranges) {
        List<Double> list = new ArrayList<>();
        List<Double> amplitudesRanges = spectra.getAmplitude();
        List<Double> phaseRanges = spectra.getPhase();
        for (int i=0; i<N; i++) {
            double sum = 0;
            for (int j = ranges.get(0); j < ranges.get(1); j++) {
                sum += amplitudesRanges.get(j) * Math.cos(2 * Math.PI * j * i / N - phaseRanges.get(j));
            }
            for (int j = ranges.get(2); j < ranges.get(3); j++) {
                sum += amplitudesRanges.get(j) * Math.cos(2 * Math.PI * j * i / N - phaseRanges.get(j));
            }
            list.add(sum);
        }
        return list;
    }

    public List<Double> restoreHigh(Spectra spectra, double cut) {
        List<Double> list = new ArrayList<>();
        List<Double> amplitudesRanges = spectra.getAmplitude();
        List<Double> phaseRanges = spectra.getPhase();
        for (int i=0; i<N; i++) {
            double sum = 0;
            for (int j = 30; j > cut; j--) {
                sum += amplitudesRanges.get(j) * Math.cos(2 * Math.PI * j * i / N - phaseRanges.get(j));
            }
            list.add(sum);
        }
        return list;
    }

    public List<Double> restoreLow(Spectra spectra, double cut) {
        List<Double> amplitudesRanges = spectra.getAmplitude();
        List<Double> phaseRanges = spectra.getPhase();
        List<Double> list = new ArrayList<>();
        for (int i=0; i<N; i++) {
            double sum = 0;
            for (int j = 0; j < cut; j++) {
                sum += amplitudesRanges.get(j) * Math.cos(2 * Math.PI * j * i / N - phaseRanges.get(j));
            }
            list.add(sum);
        }
        return list;
    }

    public List<Double> createPolyHarmonicSignal() {
        List<Signal> signals = createSignals();
        return createSinusoid(signals);
    }

    private List<Double> createSinusoid(List<Signal> signals) {
        List<Double> list = new ArrayList<>();
        for (int i =0; i<N; i++) {
            double sum = 0;
            for (int j=0; j<K; j++) {
                sum += signals.get(j).getA() * Math.cos(2 * Math.PI * signals.get(j).getF() * i / N - signals.get(j).getPhi());
            }
            list.add(sum);
        }
        return list;
    }

    private List<Signal> createSignals() {
        List<Signal> signals = new ArrayList<>();
        for (int i=0; i<K; i++) {
            signals.add(new Signal(
                    AMPLITUDES.get((int) (Math.random() * AMPLITUDES.size() % AMPLITUDES.size())),
                    (double)i + 1,
                    PHASES.get((int) (Math.random() * PHASES.size() % PHASES.size()))
            ));
        }
        return signals;
    }
}
