package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;

import java.util.Random;

/**
 * Created by sixstring982 on 6/25/15.
 */
public class Water implements Animation {
    private final static double MAX_OSCILLATION = 32;
    private final static double DELTA_THETA = 0.05;

    private double[][] greenWeights;
    private double[][] blueWeights;
    private Random rand = new Random();
    private double theta = 0.0;

    private void setupGreenWeights() {
        greenWeights = new double[Screen.WIDTH][Screen.HEIGHT];
        blueWeights = new double[Screen.WIDTH][Screen.HEIGHT];
        for (int x = 0; x < greenWeights.length; x++) {
            for (int y = 0; y < greenWeights[x].length; y++) {
                greenWeights[x][y] = 2 * (rand.nextDouble() - 0.5) * MAX_OSCILLATION;
                blueWeights[x][y] = 2 * (rand.nextDouble() - 0.5) * MAX_OSCILLATION;
            }
        }
    }

    @Override
    public void setup() {
        setupGreenWeights();
    }

    @Override
    public void loop(Screen screen) {
        theta += DELTA_THETA;
        double cx = Math.sin(theta);

        for (int x = 0; x < Screen.WIDTH; x++) {
            for (int y = 0; y < Screen.HEIGHT; y++) {
                screen.setPixel(x, y,
                        generateColor(64 + cx * blueWeights[x][y],
                                      32 + cx * greenWeights[x][y]));
            }
        }

        if (theta > Math.PI * 2) {
            theta -= Math.PI * 2;
            setupGreenWeights();
        }
    }

    private int generateColor(double blue, double green) {
        return ((int)blue & 0xff) | (((int)green & 0xff) << 8);
    }
}
