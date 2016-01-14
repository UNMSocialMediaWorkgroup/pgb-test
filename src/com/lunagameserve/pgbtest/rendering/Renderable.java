package com.lunagameserve.pgbtest.rendering;

import com.lunagameserve.pgbtest.Screen;

import java.io.IOException;

/**
 * Created by sixstring982 on 1/14/16.
 */
public interface Renderable {
    public void render(Screen screen) throws IOException;
}
