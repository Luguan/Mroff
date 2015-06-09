package com.Luguan.Mroff;

import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mroff extends Game {

	private static Mroff instance;
	public static Mroff getInstance() {
		return instance;
	}

	public Mroff() {
		instance = this;
	}

	private AssetManager assetManager;
	private boolean isLoading;

	@Override
	public void create () {
		loadAssets();
		setScreen(new GameScreen());
	}

	private void loadAssets() {
		List<String> textures = new ArrayList<String>(Arrays.asList(new String[]{"Body","tiles/dirt","tiles/sky",
				"tiles/grass","tiles/cannon","tiles/stone","tiles/green flag","tiles/red flag"}));

		assetManager = new AssetManager();

		for(String texture : textures) {
			assetManager.load("textures/" + texture + ".png", Texture.class);
		}

		assetManager.load("audio/axeSwing.mp3", Sound.class);
	}

	@Override
	public void render() {
		if(isLoading) {
			if (assetManager.update()) {
				isLoading = false;
			}
		}
		super.render();
	}

	public Texture getTexture(String texture) {
		return assetManager.get("textures/" + texture + ".png", Texture.class);
	}

	public void playSound(String file) {
		assetManager.get("audio/" + file + ".mp3", Sound.class).play();
	}
}
