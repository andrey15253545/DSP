package bsuir.DSP.lab.controller;

import bsuir.DSP.lab.service.FourthLabService;
import bsuir.DSP.lab.service.ThirdLabService;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;

import java.util.List;

public class FourthLabController {

    private static final FourthLabService service = new FourthLabService();
    private static final ThirdLabService oldService = new ThirdLabService();
    public Button signal;
    public Button slidingWindow;
    public Button parabola;
    public Button median;
    public LineChart chart;
    private List<Double> listSignal;
    private List<Double> listSlidingWindow;
    private List<Double> listParabola;
    private List<Double> listMedian;


    public void createSignal() {
        if (listSignal == null) {
            listSignal = service.createSignal();
        }
        draw(listSignal, "signal");
    }

    public void createSlidingWindow() {
        if (listSlidingWindow == null) {
            listSlidingWindow = service.createSlidingWindow(listSignal);
        }
        draw(listSlidingWindow, "sliding window");
    }

    public void createParabola() {
        if (listParabola == null) {
            listParabola = service.createParabola(listSignal);
        }
        draw(listSlidingWindow, "sliding window");
    }

    public void createMedian() {
        if (listMedian == null) {
            listMedian = service.createMedian(listSignal);
        }
        draw(listMedian, "sliding window");
    }

    private void draw(List<Double> list, String name) {
        chart.getData().clear();
        chart.getData().add(oldService.createSeries(list, name));
    }

}
