package com.lunagameserve.pgbtest;

import java.io.IOException;

/**
 * Created by sixstring982 on 6/22/15.
 */
public class Screen {
    public static final double FPS = 50;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 16;

    private int[][] pixels;

    private long lastFrame = System.currentTimeMillis();

    public interface PixelFunctor {
        int forPixel(int x, int y);
    }

    public Screen() {
        pixels = new int[WIDTH][];
        for (int x = 0; x < WIDTH; x++) {
            pixels[x] = new int[HEIGHT];
            for (int y = 0; y < HEIGHT; y++) {
                pixels[x][y] = 0;
            }
        }
    }

    public void setPixel(int x, int y, int c) {
        this.pixels[x][y] = c;
    }

    public int getPixel(int x, int y) {
        return this.pixels[x][y];
    }

    public void dimAll(int amt) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int newc = getPixel(x, y);
                newc = Color.dim(newc, amt);
                setPixel(x, y, Color.dim(newc, amt));
            }
        }
    }

    public void render(PGBSocket socket) throws IOException, InterruptedException {
            /* Swap loop order, raster is transposed! */
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                socket.writePixel(pixels[x][y]);
            }
        }
        clampFramerate();
    }

    private void clampFramerate() throws InterruptedException {
        int sleepMs = (int)(((1000.0) / FPS) - (System.currentTimeMillis() - lastFrame));
        if (sleepMs > 0) {
            Thread.sleep(sleepMs);
        }
        lastFrame = System.currentTimeMillis();
    }

    public void setAll(int c) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setPixel(x, y, c);
            }
        }
    }

    public void forAll(PixelFunctor functor) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setPixel(x, y, functor.forPixel(x, y));
            }
        }
    }
}
