package com.lunagameserve.pgbtest.animations;

import com.lunagameserve.pgbtest.Color;
import com.lunagameserve.pgbtest.Screen;

/**
 * Created by sixstring982 on 7/2/15.
 */
public class CircleChase implements Animation {

    private final int[][] circleMap;
    private int index = 0;

    public CircleChase() {
        circleMap = initCircleMap();
    }

    @Override
    public void setup() {
    }

    @Override
    public void loop(Screen screen) {
        index += 16;
        index %= Color.mapTotal;

        for (int x = 0; x < Screen.WIDTH; x++) {
            for (int y = 0; y < Screen.HEIGHT; y++) {
                screen.setPixel(x, y,
                        Color.map(index + 32 * circleMap[x][y]));
            }
        }
    }

    private int[][] initCircleMap() {
        int[][] map = new int[Screen.WIDTH][];
        double cx = Screen.WIDTH / 2.0;
        double cy = Screen.HEIGHT / 2.0;
        for (int x = 0; x < Screen.WIDTH; x++) {
            map[x] = new int[Screen.HEIGHT];
            for (int y = 0; y < Screen.HEIGHT; y++) {
                map[x][y] =
                        (int)
                        Math.floor(
                        Math.sqrt((x - cx) * (x - cx) +
                                (y - cy) * (y - cy)));
            }
        }

        return map;
    }
}
