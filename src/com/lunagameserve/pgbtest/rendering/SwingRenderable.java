package com.lunagameserve.pgbtest.rendering;

import com.lunagameserve.pgbtest.Screen;
import com.lunagameserve.pgbtest.rendering.gui.SwingRenderablePanel;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by sixstring982 on 1/14/16.
 */
public class SwingRenderable implements Renderable {
    private SwingRenderablePanel panel = new SwingRenderablePanel();
    private JFrame frame = new JFrame();

    public SwingRenderable() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.add(panel);
                frame.pack();

                frame.setDefaultCloseOperation(
                        WindowConstants.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void render(final Screen screen) throws IOException {
        panel.setScreen(screen);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                panel.repaint();
                frame.repaint();
            }
        });
    }

    @Override
    public void stop() {
        frame.dispose();
    }
}
