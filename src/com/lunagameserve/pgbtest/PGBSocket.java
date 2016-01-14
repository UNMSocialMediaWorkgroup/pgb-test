package com.lunagameserve.pgbtest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by sixstring982 on 6/24/15.
 */
public class PGBSocket {

    private Socket socket = null;

    public void connect(String server, int port) throws IOException, InterruptedException {
        socket = new Socket(server, port);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        while (in.available() > 0) {
            System.out.print((char)in.read());
        }

        out.write("doublequarterpounderwithcheese\n".getBytes("ASCII"));

        Thread.sleep(1000);

        while (in.available() > 0) {
            System.out.print((char)in.read());
        }

        out.write("begin\n".getBytes("ASCII"));

        Thread.sleep(1000);

        while (in.available() > 0) {
            System.out.print((char)in.read());
        }
    }

    public void disconnect() throws IOException {
        /* This closes the connection. */
        for (int i = 0; i < 0xff; i++) {
            socket.getOutputStream().write(0x01);
        }

        socket.close();
    }

    public void writePixel(int pixel) throws IOException {
        socket.getOutputStream().write(pixel >> 16);
        socket.getOutputStream().write(pixel >> 8);
        socket.getOutputStream().write(pixel);
    }
}
