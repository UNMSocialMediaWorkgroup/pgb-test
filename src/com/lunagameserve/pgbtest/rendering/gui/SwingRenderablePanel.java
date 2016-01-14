package com.lunagameserve.pgbtest.rendering.gui;

import com.lunagameserve.pgbtest.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sixstring982 on 1/14/16.
 */
public class SwingRenderablePanel extends JPanel {
    private final static int PIXEL_SIZE = 8;
    private final static int PIXEL_X_SPACING = 64;
    private int width = Screen.HEIGHT * (PIXEL_X_SPACING + PIXEL_SIZE);
    private int height = Screen.WIDTH * PIXEL_SIZE;
    private Screen screen;

    public SwingRenderablePanel() {
        this.setPreferredSize(new Dimension(width, height));
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    protected void paintComponent(Graphics crapg) {
        Graphics2D g = (Graphics2D)crapg;

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);

        int screenX;
        int screenY;
        for (int x = 0; x < screen.getWidth(); x++) {
            screenY = x * PIXEL_SIZE;
            for (int y = 0; y < screen.getHeight(); y++) {
                screenX = y * (PIXEL_SIZE + PIXEL_X_SPACING);

                g.setColor(new Color(screen.getPixel(x, y)));
                g.fillRect(screenX, screenY, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }
}
