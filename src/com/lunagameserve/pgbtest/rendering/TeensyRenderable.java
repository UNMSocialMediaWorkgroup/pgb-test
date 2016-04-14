package com.lunagameserve.pgbtest.rendering;

import com.lunagameserve.pgbtest.Screen;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by smw on 4/13/16.
 */
public class TeensyRenderable implements Renderable {
    private static final int CONNECT_TIMEOUT = 2000;
    private static final int DATA_RATE = 9600;
    private static final String[] PORT_NAMES = {
            "/dev/ttyACM0",
            "/dev/ttyACM1" // TODO Change these to their aliases once we hook up the teensies
    };

    private OutputStream[] outputs = new OutputStream[PORT_NAMES.length];


    public void connect() {
        CommPortIdentifier i;
        int idx = 0;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        for (i = (CommPortIdentifier)portEnum.nextElement();
             portEnum.hasMoreElements(); i = (CommPortIdentifier)portEnum.nextElement()) {
            for (String portName : PORT_NAMES) {
                if (portName.endsWith(i.getName())) {
                    try {
                        SerialPort port = (SerialPort)i.open("", CONNECT_TIMEOUT);
                        port.setSerialPortParams(DATA_RATE,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);

                        outputs[idx++] = port.getOutputStream();
                    } catch (PortInUseException | UnsupportedCommOperationException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void render(Screen screen) throws IOException {

    }

    @Override
    public void stop() {
        for (OutputStream out : outputs) {
            try {
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
