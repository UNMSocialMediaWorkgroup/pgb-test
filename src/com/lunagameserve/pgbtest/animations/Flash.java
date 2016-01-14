package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;

/**
 * Created by sixstring982 on 6/28/15.
 */
public class Flash implements Animation {

    boolean on = false;
    @Override
    public void setup() {

    }

    @Override
    public void loop(Screen screen) {
        if (!on) {
            screen.setAll(0x200020);
        } else {
            screen.setAll(0);
        }
        on = !on;
    }
}
