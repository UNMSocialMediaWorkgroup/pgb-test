package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class BeamEngine implements Animation {

    private static double THETA_DELTA = 0.1;
    private double theta = 0.0;


    @Override
    public void setup() {

    }

    @Override
    public void loop(Screen screen) {
        theta += THETA_DELTA;
        screen.forAll(pfunc);
    }

    private Screen.PixelFunctor pfunc = (x, y) -> {
        if (Math.sin(theta * 3 + x * 0.4 + (theta * 1.0 * y) * 1.2) > 0) {
            return 0x00ff00;
        }

        return 0;
    };
}
