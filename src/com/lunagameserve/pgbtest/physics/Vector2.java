package com.lunagameserve.pgbtest.physics;

import java.util.Random;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class Vector2 {
    public final double x;
    public final double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 negX() {
        return new Vector2(-x, y);
    }

    public Vector2 negY() {
        return new Vector2(x, -y);
    }

    public double mod() {
        return Math.sqrt(x * x + y * y);
    }

    public static Vector2 rand(Random rand,
                               double maxx, double maxy) {
        return new Vector2(rand.nextDouble() * maxx,
                rand.nextDouble() * maxy);
    }
}
