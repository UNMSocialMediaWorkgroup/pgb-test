package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Color;
import com.lunagameserve.pgbtest.Screen;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class Indexing implements Animation {

    int[] whitePtrs = new int[Screen.HEIGHT];
    int[] blackPtrs = new int[Screen.HEIGHT];
    int start = 0;

    @Override
    public void setup() {
        for (int x = 0; x < Screen.HEIGHT; x++) {
            whitePtrs[x] = x + 1;
            blackPtrs[x] = 0;
        }
    }

    @Override
    public void loop(Screen screen) {
        for (int y = 0; y < Screen.HEIGHT; y++) {
            screen.setPixel((whitePtrs[y] + start) % Screen.WIDTH, y, 0xffffff);
            screen.setPixel((blackPtrs[y] + start) % Screen.WIDTH, y, 0x000000);
        }
        start++;
        start %= Screen.WIDTH;
    }
}
