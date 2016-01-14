package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by sixstring982 on 1/14/16.
 */
public class Popcorn implements Animation {
    private static final double RED_LAMBDA = 0.01;
    private static final double GREEN_LAMBDA = 0.015;
    private static final double BLUE_LAMBDA = 0.02;

    private static final int RED_ITERATIONS = 32;
    private static final int GREEN_ITERATIONS = 64;
    private static final int BLUE_ITERATIONS = 96;

    private static final double[] redTs = new double[] {
            0.1, 1.9, 0.3, 1.4
    };

    private static final double[] greenTs = new double[] {
            1.5, 0.3, 1.4, 0.8
    };

    private static final double[] blueTs = new double[] {
            0.3, 2.5, 1.1, 0.2
    };

    private static final int ROUNDS_PER_UPDATE = 8;
    private static final int FRAMES_PER_FIELD = 1024;

    private static final double SCALING_FACTOR = 1.0 / 32.0;
    private static final int LOG_WORTH = 32;
    private static final double WEIGHTING_FACTOR = 0.9;

    private int currentVectorField = 0;
    private int currentFrame = 0;

    private int[][] redBins;
    private int[][] greenBins;
    private int[][] blueBins;
    private final Random rand = new Random(System.currentTimeMillis());



    private interface VectorField {
        public Point2D.Double crunch(Point2D.Double pt, double[] t);
    }

    private VectorField[] fields = new VectorField[]{
            new VectorField() {
                @Override
                public Point2D.Double crunch(Point2D.Double pt, double[] t) {
                    double x = Math.cos(t[0] + pt.y + Math.cos(t[1] + Math.PI * pt.x));
                    double y = Math.cos(t[2] + pt.x + Math.cos(t[3] + Math.PI * pt.y));

                    return new Point2D.Double(x, y);
                }
            },
            new VectorField() {
                @Override
                public Point2D.Double crunch(Point2D.Double pt, double[] t) {
                    double x = Math.sin(t[0] + pt.y + Math.cos(t[1] + Math.PI * pt.x));
                    double y = Math.cos(t[2] + pt.x + Math.sin(t[3] + Math.PI * pt.y));

                    return new Point2D.Double(x, y);
                }
            },
            new VectorField() {
                @Override
                public Point2D.Double crunch(Point2D.Double pt, double[] t) {
                    double x = Math.sin(t[0] * pt.y + Math.cos(t[1] * Math.PI * pt.x));
                    double y = Math.cos(t[2] * pt.x + Math.sin(t[3] * Math.PI * pt.y));

                    return new Point2D.Double(x, y);
                }
            },
            new VectorField() {
                @Override
                public Point2D.Double crunch(Point2D.Double pt, double[] t) {
                    double x = Math.cos(t[0] * pt.y + Math.cos(t[1] * Math.PI * pt.x));
                    double y = Math.cos(t[2] * pt.x + Math.cos(t[3] * Math.PI * pt.y));

                    return new Point2D.Double(x, y);
                }
            }
    };

    @Override
    public void setup() {
        redBins = new int[Screen.WIDTH][Screen.HEIGHT];
        greenBins = new int[Screen.WIDTH][Screen.HEIGHT];
        blueBins = new int[Screen.WIDTH][Screen.HEIGHT];
    }

    @Override
    public void loop(Screen screen) {
        currentFrame++;
        if (currentFrame >= FRAMES_PER_FIELD) {
            currentFrame = 0;
            currentVectorField = Math.abs(rand.nextInt()) % fields.length;
            setup();
        }

        for (int i = 0; i < ROUNDS_PER_UPDATE; i++) {
            doRound(screen.getWidth(), screen.getHeight());
        }

        for (int x = 0; x < screen.getWidth(); x++) {
            for (int y = 0; y < screen.getHeight(); y++) {
                int r = Math.min((int) (qdrt(redBins[x][y]) * LOG_WORTH), 255);
                int g = Math.min((int) (qdrt(greenBins[x][y]) * LOG_WORTH), 255);
                int b = Math.min((int) (qdrt(blueBins[x][y]) * LOG_WORTH), 255);

                if (r > g && r > b) {
                    g = (int)(g * WEIGHTING_FACTOR);
                    b = (int)(b * WEIGHTING_FACTOR);
                } else if (g > r && g > b) {
                    r = (int)(r * WEIGHTING_FACTOR);
                    b = (int)(b * WEIGHTING_FACTOR);
                } else if (b > r && b > g) {
                    r = (int)(r * WEIGHTING_FACTOR);
                    g = (int)(g * WEIGHTING_FACTOR);
                }

                screen.setPixel(x, y, 0xff000000 | (r << 16) | (g << 8) | b);
            }
        }
    }

    private double qdrt(double x) {
        return Math.sqrt(Math.sqrt(x));
    }

    private void doRound(double width, double height) {
        int[] peaks = new int[] {RED_ITERATIONS,
                                 GREEN_ITERATIONS,
                                 BLUE_ITERATIONS};

        int[][][] bins = new int[][][] {redBins, greenBins, blueBins};
        double[] lambdas = new double[] {RED_LAMBDA, GREEN_LAMBDA, BLUE_LAMBDA};

        double[][] ts = new double[][] {redTs, greenTs, blueTs};

        for (int i = 0; i < 3; i++) {
            Point2D.Double pt = new Point2D.Double(
                    rand.nextDouble() * width * SCALING_FACTOR,
                    rand.nextDouble() * height * SCALING_FACTOR
            );

            for (int j = 0; j < peaks[i]; j++) {
                pt = euler(lambdas[i], ts[i], pt);
                if (pt.x > 0.0 && pt.x < (width * SCALING_FACTOR) &&
                    pt.y > 0.0 && pt.y < (height * SCALING_FACTOR)) {
                    bins[i][(int)(pt.x / SCALING_FACTOR)]
                           [(int)(pt.y / SCALING_FACTOR)]++;
                }
            }
        }
    }

    private Point2D.Double euler(double lambda, double[] t, Point2D.Double pt) {
        Point2D.Double v = fields[currentVectorField].crunch(pt, t);
        return new Point2D.Double(pt.x + lambda * v.x,
                                  pt.y + lambda * v.y);
    }
}
