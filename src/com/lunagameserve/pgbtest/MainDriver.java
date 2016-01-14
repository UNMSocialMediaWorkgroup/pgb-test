package com.lunagameserve.pgbtest;

import com.lunagameserve.pgbtest.animations.*;
import com.lunagameserve.pgbtest.rendering.PGBSocketRenderable;
import com.lunagameserve.pgbtest.rendering.Renderable;
import com.lunagameserve.pgbtest.rendering.SwingRenderable;
import com.lunagameserve.pgbtest.rendering.TeeRenderable;

import java.io.IOException;
import java.util.Random;

/**
 * Created by sixstring982 on 6/22/15.
 */
public class MainDriver {
    public static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        // PGBSocket socket = new PGBSocket();
        // socket.connect("192.168.1.130", 6982);

        // run(socket);
        run(null);

        // socket.disconnect();
    }

    private static void run(PGBSocket socket) throws IOException,
                                                     InterruptedException {
        boolean running = true;
        Screen screen = new Screen();
        Animation anim = new Popcorn();
        anim.setup();

        Renderable renderable = new TeeRenderable(
                new PGBSocketRenderable(socket),
                new SwingRenderable()
        );

        while (running) {
            if (System.in.available() > 0) {
                running = false;
            }

            anim.loop(screen);

            renderable.render(screen);
            screen.clampFramerate();
        }

        renderable.stop();
    }
}
