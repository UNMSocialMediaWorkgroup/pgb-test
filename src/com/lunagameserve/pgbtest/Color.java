package com.lunagameserve.pgbtest;

/**
 * Created by sixstring982 on 6/24/15.
 */
public class Color {

    public static int dim(int c, int amt) {
        int r = (c & 0xff0000) >> 16;
        int g = (c & 0x00ff00) >> 8;
        int b = (c & 0x0000ff);

        r >>= amt;
        g >>= amt;
        b >>= amt;

        return (r << 16) | (g << 8) | b;
    }

    public static int mapTotal = 0x5ff;

    public static int map(int index) {
        index %= mapTotal;
        int low = index & 0xff;
        int lowc = 0xff - low;
        switch ((index & 0x700)) {
            case 0x000: return 0xff0000 | low;
            case 0x100: return (lowc << 16) | 0xff;
            case 0x200: return (low << 8) | 0xff;
            case 0x300: return 0xff00 | lowc;
            case 0x400: return (low << 16) | 0xff00;
            case 0x500: return 0xff0000 | (lowc << 8);
        }
        return 0;
    }
}
