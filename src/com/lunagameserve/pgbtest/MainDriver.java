package com.lunagameserve.pgbtest;

import com.lunagameserve.pgbtest.animations.*;

import java.io.IOException;
import java.util.Random;

/**
 * Created by sixstring982 on 6/22/15.
 */
public class MainDriver {

    public static Random rand = new Random();

    public static void main(String[] args) throws Exception {
        PGBSocket socket = new PGBSocket();
        socket.connect("192.168.1.130", 6982);

        run(socket);

        socket.disconnect();
    }

    private static void run(PGBSocket socket) throws IOException, InterruptedException {
        boolean running = true;
        Screen screen = new Screen();
        Animation anim = new SinScope();
        anim.setup();

        while (running) {
            if (System.in.available() > 0) {
                running = false;
            }

            anim.loop(screen);

            screen.render(socket);
        }
    }
}
