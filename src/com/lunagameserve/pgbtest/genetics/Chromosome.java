package com.lunagameserve.pgbtest.genetics;

import com.lunagameserve.pgbtest.animations.Paternity;

import java.util.Random;

/**
 * Created by sixstring982 on 1/18/16.
 */
public class Chromosome {
    public static final int MAX_VALUE = 255;

    private static final int CROSSOVER_MARGIN = 10;

    private final int[] bands;

    public Chromosome(int length) {
        if (length < 3 * CROSSOVER_MARGIN) {
            throw new IllegalArgumentException("Crossover length must be " +
                    "at least " + (3 * CROSSOVER_MARGIN) + " to account " +
                    "for margins.");
        }
        bands = new int[length];
    }

    public Chromosome(int[] bands) {
        this.bands = new int[bands.length];
        System.arraycopy(bands, 0, this.bands, 0, bands.length);
    }

    public Chromosome randomize() {
        double d;
        for (int i = 0; i < bands.length; i++) {
            d = Paternity.rand.nextDouble();
            d = d * d * d * d;
            bands[i] = (int)(MAX_VALUE * d);
        }
        return this;
    }

    public int getLength() {
        return this.bands.length;
    }

    public int getBand(int location) {
        return this.bands[location];
    }

    public boolean isParentOf(Chromosome child, int band) {
        return bands[band] == child.bands[band];
    }

    public Chromosome mate(Chromosome parent) {
        int crossoverLocation = findCrossoverLocation();
        int[] newBands = new int[bands.length];
        for (int i = 0; i < bands.length; i++) {
            if (i < crossoverLocation) {
                newBands[i] = this.bands[i];
            } else {
                newBands[i] = parent.bands[i];
            }
        }
        return new Chromosome(newBands);
    }

    private int findCrossoverLocation() {
        return CROSSOVER_MARGIN +
               Paternity.rand.nextInt(bands.length - CROSSOVER_MARGIN);
    }
}
