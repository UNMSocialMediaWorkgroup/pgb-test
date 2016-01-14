package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class SinglePixel implements Animation {

    private boolean on = false;

    @Override
    public void setup() {

    }

    @Override
    public void loop(Screen screen) {
        if (!on) {
            for (int i = 0; i < Screen.HEIGHT; i++) {
                screen.setPixel(0, i, 0xffffff);
            }
        } else {
            for (int i = 0; i < Screen.HEIGHT; i++) {
                screen.setPixel(0, i, 0);
            }
        }

        on = !on;
    }
}
