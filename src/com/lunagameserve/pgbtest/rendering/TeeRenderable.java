package com.lunagameserve.pgbtest.rendering;

import com.lunagameserve.pgbtest.Screen;

import java.io.IOException;

/**
 * Created by sixstring982 on 1/14/16.
 */
public class TeeRenderable implements Renderable {
    private Renderable a;
    private Renderable b;

    public TeeRenderable(Renderable a, Renderable b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void render(Screen screen) throws IOException {
        a.render(screen);
        b.render(screen);
    }
}
