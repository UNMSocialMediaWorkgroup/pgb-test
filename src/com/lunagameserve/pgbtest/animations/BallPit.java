package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Screen;
import com.lunagameserve.pgbtest.physics.Ball;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class BallPit implements Animation {

    private static int BALL_COUNT = 512;

    Ball[] balls = new Ball[BALL_COUNT];

    @Override
    public void setup() {
        for (int i = 0; i < balls.length; i++) {
            balls[i] = new Ball();
        }
    }

    @Override
    public void loop(Screen screen) {
        for (Ball ball : balls) {
            ball.update(Screen.WIDTH, Screen.HEIGHT);
        }

        screen.forAll(pfunc);
    }

    private Screen.PixelFunctor pfunc = (x, y) -> {
        for (Ball ball : balls) {
            if (ball.occupies(x, y)) {
                return ball.getColor();
            }
        }
        return 0;
    };
}
