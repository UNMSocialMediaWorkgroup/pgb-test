package com.lunagameserve.pgbtest.rendering;

import com.lunagameserve.pgbtest.PGBSocket;
import com.lunagameserve.pgbtest.Screen;

import java.io.IOException;

/**
 * Created by sixstring982 on 1/14/16.
 */
public class PGBSocketRenderable implements Renderable {
    private final PGBSocket socket;

    public PGBSocketRenderable(PGBSocket socket) {
        this.socket = socket;
    }

    @Override
    public void render(Screen screen) throws IOException {
        if (socket != null) {
            /* Swap loop order, raster is transposed! */
            for (int y = 0; y < screen.getHeight(); y++) {
                for (int x = 0; x < screen.getWidth(); x++) {
                    socket.writePixel(screen.getPixel(x, y));
                }
            }
        }
    }

    @Override
    public void stop() {

    }
}
