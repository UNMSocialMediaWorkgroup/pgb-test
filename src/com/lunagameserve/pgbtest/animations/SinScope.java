package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;

/**
 * Created by sixstring982 on 7/7/15.
 */
public class SinScope implements Animation {
    private final double HEIGHT_THETA = (Math.PI * 2) / Screen.HEIGHT;
    private final double THETA_DELTA = 0.1;
    private double theta = 0.0;

    @Override
    public void setup() {

    }

    @Override
    public void loop(Screen screen) {
        theta += THETA_DELTA;
        theta %= Math.PI * 2;

        screen.setAll(0);
        screen.forAll(functor);
    }

    private final Screen.PixelFunctor functor = (x, y) -> {
        double sinVal = 10 * Math.sin(y * HEIGHT_THETA + theta);
        boolean xCen = Math.abs(((x + sinVal) - Screen.WIDTH / 2)) < 2      ||
                       Math.abs(((x + sinVal + 10) - Screen.WIDTH / 2)) < 2 ||
                       Math.abs(((x + sinVal - 10) - Screen.WIDTH / 2)) < 2;

        if (!xCen) {
            return 0x000000;
        } else {
            return 0x00ff00;
        }
    };
}
