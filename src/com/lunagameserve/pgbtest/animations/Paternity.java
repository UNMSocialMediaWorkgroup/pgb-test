package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Color;
import com.lunagameserve.pgbtest.Screen;
import com.lunagameserve.pgbtest.genetics.Chromosome;
import com.lunagameserve.pgbtest.genetics.PaternityState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by sixstring982 on 1/18/16.
 */
public class Paternity implements Animation {
    private final static int CHROMOSOME_COUNT = 5;
    private final static int CHROMOSOME_WIDTH = 3;
    private final static float[][] CHROMOSOME_COLORS = new float[][] {
            Color.MAGENTA,
            Color.CYAN,
            Color.YELLOW,
            Color.WHITE,
            Color.BLUE
    };
    public final static Random rand = new Random();
    private PaternityState state = PaternityState.FADE_IN;
    private int frame = 0;
    private Chromosome[] chromosomes = new Chromosome[CHROMOSOME_COUNT];
    private final static int CHROMOSOME_LENGTH = Screen.WIDTH * 3;

    @Override
    public void setup() {

    }

    @Override
    public void loop(Screen screen) {
        switch(state) {
            case FADE_IN: loopFadeIn(screen); break;
            case MATCH_ONE: loopMatch(screen, 2); break;
            case MATCH_TWO: loopMatch(screen, 3); break;
            case MATCH_THREE: loopMatch(screen, 4); break;
            case FLASH_ONE: loopFlash(screen, 2); break;
            case FLASH_TWO: loopFlash(screen, 3); break;
            case FLASH_THREE: loopFlash(screen, 4); break;
            case FADE_OUT: loopFadeOut(screen); break;
            default:
                break;
        }

        if (frame < getStateLength()) {
            frame++;
        } else {
            state = PaternityState.getNext(state);
            frame = 0;
        }
    }

    private void loopFadeIn(Screen screen) {
        if (frame == 0) {
            generateChromosomes();
        }

        float intensityScale = (float)frame / (float)getStateLength();
        renderChromosomes(screen, intensityScale);
    }

    private void loopMatch(Screen screen, int matchIdx) {
        renderChromosomes(screen);
        int band = frame;
        boolean isMom = chromosomes[0].isParentOf(chromosomes[matchIdx], band);
        boolean isDad = chromosomes[1].isParentOf(chromosomes[matchIdx], band);

        int firstX = frame % screen.getWidth();
        int firstY = frame / screen.getWidth();

        float[][] colors = new float[][] {
                isMom ? Color.GREEN : Color.RED,
                isDad ? Color.GREEN : Color.RED,
                isMom || isDad ? Color.GREEN : Color.RED
        };
        int[] ys = new int[] {firstY,
                              firstY + CHROMOSOME_WIDTH,
                              firstY + (CHROMOSOME_WIDTH * matchIdx)};
        for (int i = 0; i < 3; i++) {
            screen.setPixel(firstX, ys[i], Color.fromFloats(colors[i]));
        }
    }

    private void loopFlash(Screen screen, int matchIdx) {
        renderChromosomes(screen);
        double theta = 4.0 * Math.PI * getStateProgress();
        float intensity = (float)(0.5 + 0.5 * Math.cos(theta));
        boolean isMom =
                chromosomes[0].isParentOf(chromosomes[matchIdx], 0) ||
                chromosomes[0].isParentOf(chromosomes[matchIdx],
                                          CHROMOSOME_LENGTH - 1);
        boolean isDad =
                chromosomes[1].isParentOf(chromosomes[matchIdx], 0) ||
                chromosomes[1].isParentOf(chromosomes[matchIdx],
                                          CHROMOSOME_LENGTH - 1);

        float[] childColor;
        if (isMom && isDad) {
            childColor = CHROMOSOME_COLORS[matchIdx];
        } else if (isMom) {
            childColor = CHROMOSOME_COLORS[0];
        } else if (isDad) {
            childColor = CHROMOSOME_COLORS[1];
        } else {
            childColor = Color.RED;
        }

        float[][] colors = new float[][]{
                isMom ? CHROMOSOME_COLORS[0] : Color.RED,
                isDad ? CHROMOSOME_COLORS[1] : Color.RED,
                childColor
        };

        for (int x = 0; x < screen.getWidth(); x++) {
            for (int y = 0; y < screen.getHeight() - 1; y++) {
                int cid = y / CHROMOSOME_WIDTH;
                int cY = y % CHROMOSOME_WIDTH;
                float cIntensity =
                        chromosomes[cid].getBand(x + cY * screen.getWidth()) /
                                (float)Chromosome.MAX_VALUE;

                float[] color;
                if (cid == 0) {
                    color = colors[0];
                } else if (cid == 1) {
                    color = colors[1];
                } else if (cid == matchIdx) {
                    color = colors[2];
                } else {
                    continue;
                }

                cIntensity *= intensity;

                screen.setPixel(x, y, Color.fromFloats(color, cIntensity));
            }

        }
    }

    private void loopFadeOut(Screen screen) {
        float intensity = 1.0f;
        float extraIntensity = Chromosome.MAX_VALUE *
                               (float)Math.pow(getStateProgress(), 10.0);


        intensity += extraIntensity;
        renderChromosomes(screen, intensity);
    }

    private void renderChromosomes(Screen screen) {
        renderChromosomes(screen, 1.0f);
    }

    private void renderChromosomes(Screen screen, float intensityScale) {
        for (int x = 0; x < screen.getWidth(); x++) {
            for (int y = 0; y < screen.getHeight() - 1; y++) {
                int cid = y / CHROMOSOME_WIDTH;
                int cY = y % CHROMOSOME_WIDTH;
                float intensity =
                        chromosomes[cid].getBand(x + cY * screen.getWidth()) /
                        (float)Chromosome.MAX_VALUE;

                intensity *= intensityScale;
                float[] color = CHROMOSOME_COLORS[cid];

                if (intensity >= 0.0f && intensity <= 1.0f) {
                    screen.setPixel(x, y, Color.fromFloats(color, intensity));
                } else {
                    screen.setPixel(x, y, 0);
                }
            }
        }
    }

    private int getStateLength() {
        if (state.getLength() == 0) {
            return CHROMOSOME_LENGTH - 1;
        } else {
            return state.getLength();
        }
    }

    private float getStateProgress() {
        return (float)frame / (float)getStateLength();
    }

    private void generateChromosomes() {
            /* Generate parents */
        Chromosome[] parents = new Chromosome[] {
                new Chromosome(CHROMOSOME_LENGTH).randomize(),
                new Chromosome(CHROMOSOME_LENGTH).randomize(),
                new Chromosome(CHROMOSOME_LENGTH).randomize()
        };

            /* Generate children */
        chromosomes[2] = parents[0].mate(parents[1]);
        chromosomes[3] = parents[0].mate(parents[2]);
        chromosomes[4] = parents[1].mate(parents[2]);

            /* Use two random parents */
        List<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < 3; i++) {
            ids.add(i);
        }
        Collections.shuffle(ids);
        chromosomes[0] = parents[ids.get(0)];
        chromosomes[1] = parents[ids.get(1)];
    }
}
