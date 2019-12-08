package bsuir.DSP.lab.service;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class SecondLabService {


    private static final XYChart.Series series1 = new XYChart.Series<Double, Double>();
    private static final XYChart.Series series2 = new XYChart.Series<Double, Double>();
    private static final XYChart.Series series3 = new XYChart.Series<Double, Double>();

    static {
        series1.setName("F1");
        series2.setName("F2");
        series3.setName("Amplitude");
    }

    public void setLineChart(LineChart<Double, Double> lineChart) {
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        lineChart.getData().add(series3);
    }

    public void create(double K, double Phi) {
        int N = 1024;
        List<XYChart.Data> list1 = new ArrayList<>();
        List<XYChart.Data> list2 = new ArrayList<>();
        List<XYChart.Data> list3 = new ArrayList<>();
        for (int M = (int)K; M <= 2 * N; M ++) {
            double rms1 = 0, rms2 = 0;
            double Re = 0, Im = 0;
            for (int n = 0; n<=M; n++) {
                double t = Math.sin(2 * Math.PI * n / N + Phi / 180 * Math.PI);
                rms1 += Math.pow(t,2);
                rms2 += t;
                Re += t * Math.cos(2* Math.PI * n / M);
                Im += t * Math.sin(2* Math.PI * n / M);
            }

            list1.add(new XYChart.Data<>(M, 0.707 - Math.sqrt(rms1 / (M+1))));
            list2.add(new XYChart.Data<>(M, 0.707 - (Math.sqrt(rms1 / (M+1) - Math. pow(rms2 / (M+1), 2)))));
            list3.add(new XYChart.Data<>(M, 1 - Math.sqrt(Math.pow(2*Re / M, 2) + Math.pow(2*Im /M, 2))));
        }
        series1.getData().clear();
        series3.getData().clear();
        series2.getData().clear();
        series1.getData().addAll(list1);
        series2.getData().addAll(list2);
        series3.getData().addAll(list3);
    }

}
