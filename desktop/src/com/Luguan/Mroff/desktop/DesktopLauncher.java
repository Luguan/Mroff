package com.Luguan.Mroff.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.Luguan.Mroff.Mroff;

public class DesktopLauncher implements Mroff.NativeWindowManager{

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Mroff(new DesktopLauncher()), config);
	}

	@Override
	public boolean goFullscreen() {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		return Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, false);

	}

	@Override
	public boolean goWindowMode(int width, int height) {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
		return Gdx.graphics.setDisplayMode(width, height, false);
	}
}
