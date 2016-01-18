package com.lunagameserve.pgbtest;

/**
 * Created by sixstring982 on 6/24/15.
 */
public class Color {
    public static final float[] WHITE   = new float[] {1.0f, 1.0f, 1.0f};
    public static final float[] MAGENTA = new float[] {1.0f, 0.0f, 1.0f};
    public static final float[] CYAN    = new float[] {0.0f, 1.0f, 1.0f};
    public static final float[] YELLOW  = new float[] {1.0f, 1.0f, 0.0f};
    public static final float[] RED     = new float[] {1.0f, 0.0f, 0.0f};
    public static final float[] GREEN   = new float[] {0.0f, 1.0f, 0.0f};
    public static final float[] BLUE    = new float[] {0.0f, 0.0f, 1.0f};

    public static int dim(int c, int amt) {
        int r = (c & 0xff0000) >> 16;
        int g = (c & 0x00ff00) >> 8;
        int b = (c & 0x0000ff);

        r >>= amt;
        g >>= amt;
        b >>= amt;

        return (r << 16) | (g << 8) | b;
    }

    public static int fromFloats(float[] color, float i) {
        return fromFloats(color[0] * i, color[1] * i, color[2] * i);
    }

    public static int fromFloats(float[] color) {
        return fromFloats(color[0], color[1], color[2]);
    }

    public static int fromFloats(float r, float g, float b, float i) {
        return fromFloats(r * i, g * i, b * i);
    }

    public static int fromFloats(float r, float g, float b) {
        int ir = Math.min((int)(r * 255.0f), 0xff);
        int ig = Math.min((int)(g * 255.0f), 0xff);
        int ib = Math.min((int)(b * 255.0f), 0xff);

        return (ir << 16) | (ig << 8) | ib;
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
