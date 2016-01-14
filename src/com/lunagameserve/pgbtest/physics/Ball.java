package com.lunagameserve.pgbtest.physics;

import com.lunagameserve.pgbtest.Color;
import com.lunagameserve.pgbtest.MainDriver;
import com.lunagameserve.pgbtest.Screen;

import java.util.Random;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class Ball {

    public static Random rand = MainDriver.rand;

    private Vector2 position;
    private Vector2 velocity;

    private int color = Color.map(rand.nextInt());

    public Ball() {
        position = Vector2.rand(rand, Screen.WIDTH,
                                Screen.HEIGHT);
        velocity = Vector2.rand(rand, 0.2, 0.2);
        if (rand.nextBoolean()) {
            velocity = velocity.negX();
        }

        if (rand.nextBoolean()) {
            velocity = velocity.negY();
        }
    }

    public boolean occupies(int x, int y) {
        return (int)position.x == x && (int)position.y == y;
    }

    public void update(int maxX, int maxY) {
        Vector2 temppos = position.add(velocity);
        if (temppos.x < 0 || temppos.x > maxX) {
            velocity = velocity.negX();
        }

        if (temppos.y < 0 || temppos.y > maxY) {
            velocity = velocity.negY();
        }

        position = position.add(velocity);
    }

    public int getColor() {
        return color;
    }
}
