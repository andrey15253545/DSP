package bsuir.DSP.lab.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Spectra {
    List<Double> amplitude = new ArrayList<>();
    List<Double> phase = new ArrayList<>();
}