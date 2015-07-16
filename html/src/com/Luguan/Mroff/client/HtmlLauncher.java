package com.Luguan.Mroff.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.Luguan.Mroff.Mroff;

public class HtmlLauncher extends GwtApplication implements Mroff.NativeWindowManager {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(1024, 768);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new Mroff(this);
        }

        @Override
        public boolean goFullscreen() {
                Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
                return true;
        }

        @Override
        public boolean goWindowMode(int width, int height) {
                return true;
        }
}