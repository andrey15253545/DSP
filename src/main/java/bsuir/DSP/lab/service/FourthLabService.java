package bsuir.DSP.lab.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FourthLabService {

    private static final int N = 512;
    private static final int SLIDING_WINDOW_SIZE = 5;
    private static final int MEDIAN_WINDOW_SIZE = 9;
    private static final int MEDIAN_K = 2;

    public List<Double> createSignal() {
        List<Double> list = new ArrayList<>();
        for (int i=0; i<N; i++) {
            double sum = 100 * Math.sin(2 * Math.PI * i / N);
            for (int j=50; j<70; j++ ) {
                sum += -Math.random() * 100 * Math.sin(2 * Math.PI * i * j / N);
            }
            list.add(sum);
        }
        return list;
    }

    public List<Double> createSlidingWindow(List<Double> list) {
        List<Double> list1 = new ArrayList<>();
        int m = (SLIDING_WINDOW_SIZE - 1) / 2;
        for (int i=0; i<N; i++) {
            double sum = 0;
            for (int j=Math.max(0, i - m); j<Math.min(N, i + m); j++) {
                sum+=list.get(j);
            }
            list1.add(sum / SLIDING_WINDOW_SIZE);
        }
        return list1;
    }


    public List<Double> createParabola(List<Double> listSignal) {
        List<Double> list1 = new ArrayList<>();
        for (int i=0; i<N; i++) {
            double sum = 0;
            double coefficient = 0;

            if (i - 3 > 0) {
                sum += 5 * listSignal.get(i - 3);
                coefficient += 5;
            }

            if (i - 2 > 0) {
                sum += -30 * listSignal.get(i - 2);
                coefficient -= 30;
            }

            if (i - 1 > 0) {
                sum += 75 * listSignal.get(i - 1);
                coefficient += 75;
            }

            sum += 131 * listSignal.get(i);
            coefficient += 131;

            if (i + 1 < N) {
                sum += 75 * listSignal.get(i + 1);
                coefficient += 75;
            }

            if (i + 2 < N) {
                sum += -35 * listSignal.get(i + 2);
                coefficient -= 35;
            }

            if (i + 3 < N) {
                sum += 5 * listSignal.get(i + 3);
                coefficient += 5;
            }

            list1.add(sum / coefficient);
        }
        return list1;
    }

    public List<Double> createMedian(List<Double> listSignal) {
        List<Double> list = new ArrayList<>();
        for (int i=0; i<N; i++){
            List<Double> window = new ArrayList<>();
            int startIndex = Math.max(0, i - (MEDIAN_WINDOW_SIZE - 1) / 2 + MEDIAN_K);
            int endIndex = Math.min(N, i + (MEDIAN_WINDOW_SIZE - 1) / 2 - MEDIAN_K);
            for (int j=startIndex; j<endIndex; j++)
                window.add(listSignal.get(j));
            window = window.stream().sorted().collect(Collectors.toList());
            list.add(window.get(window.size() / 2));
        }
        return list;
    }
}
