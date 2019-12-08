package bsuir.DSP.lab.service;

import bsuir.DSP.lab.model.Signal;
import javafx.scene.chart.XYChart;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FirstLabService {

    private static final int N = 512;
    private static final int SAMPLING_RATE = 44100;
    private static final Random random = new Random();
    private SignalFunction f = (signals, i) -> signals.stream().mapToDouble(signal -> signal.getA() * Math.sin(2 * Math.PI * signal.getF() * i + signal.getPhi() / 180 * Math.PI)).sum();
    private XYChart.Series series = new XYChart.Series<Double, Double>();

    public void createSinusoid(List<Signal> signals) {
        f = (s, i) -> s.stream().mapToDouble(signal -> signal.getA() * Math.sin(2 * Math.PI * signal.getF() * i + signal.getPhi() / 180 * Math.PI)).sum();
        createSignal(signals);
    }

    public void createDutyCycle(List<Signal> signals) {
        f = (s, i) -> s.stream().mapToDouble(signal -> Math.sin(2 * Math.PI * signal.getF() * i + signal.getF()) > 0.5 ? signal.getA() : 0).sum();
        createSignal(signals);
    }

    public void createTriangle(List<Signal> signals) {
        f = (s, i) -> s.stream().mapToDouble(signal -> 2 * signal.getA() / Math.PI * Math.asin(Math.sin(2 * Math.PI * signal.getF() * i + signal.getPhi()))).sum();
        createSignal(signals);
    }

    public void createSaw(List<Signal> signals) {
        f = (s, i) -> s.stream().mapToDouble(signal -> -2 * signal.getA() / Math.PI * Math.atan(1 / Math.tan(Math.PI * signal.getF() * i + signal.getPhi()))).sum();
        createSignal(signals);
    }

    public void createNoise(List<Signal> signals) {
        f = (s, i) -> s.stream().mapToDouble(signal ->  signal.getA() * random.nextDouble()).sum();
        createSignal(signals);
    }

    public void createAmplitudeModulation(List<Signal> signals) {
        series.getData().clear();
        for (int i=0; i<N / 2; i++) {
            series.getData().add(new XYChart.Data<>(i, f.execute(signals, (double) i / N) * (N / 2 - i) / (N / 2)));
        }
        for (int i= N / 2 + 1; i<N; i++) {
            series.getData().add(new XYChart.Data<>(i, f.execute(signals, (double) i / N) * (i - N +N / 2) / (N / 2)));
        }
    }

    public void createFrequencyModulation(List<Signal> signals) {
        series.getData().clear();
        for (int i = 0; i < N ; i++) {
            series.getData().add(new XYChart.Data<>(i *  i / N , f.execute(signals, (double) i / N)));
        }
    }


    public void setSeries(XYChart.Series series) {
        this.series = series;
    }

    public void createSignal(List<Signal> signals) {
        series.getData().clear();
        for (int i = 0; i<N; i++) { ;
            series.getData().add(new XYChart.Data<>(i, f.execute(signals, (double) i / N)));
        }
    }

    public void playSignal (List<Signal> signals) {
        try {
            AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line matching " + info + " is not supported.");
                throw new LineUnavailableException();
            }

            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize() * 4);
            cBuf.clear();
            List<Signal> s = signals
                    .stream()
                    .map(signal ->
                        new Signal(
                                signal.getA() * (Short.MAX_VALUE/ 20),
                                signal.getF() * (1000 / 20),
                                signal.getPhi()
                        ))
                    .collect(Collectors.toList());
            for (int n = 0; n < 5; n++) {
                cBuf.clear();
                IntStream
                        .range(0, SAMPLING_RATE)
                        .mapToDouble(x -> (double) x / SAMPLING_RATE)
                        .forEach(i -> cBuf.putShort((short) f.execute(s, i).floatValue()));
                line.write(cBuf.array(), 0, cBuf.position());
            }
            line.drain();
            line.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private interface SignalFunction {
        Double execute (List<Signal> signals, double i);
    }

}
