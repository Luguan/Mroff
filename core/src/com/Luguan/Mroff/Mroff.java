package com.Luguan.Mroff;

import com.Luguan.Mroff.screen.GameScreen;
import com.Luguan.Mroff.screen.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

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
	private boolean isLoading = true;

	@Override
	public void create () {
		loadAssets();
		setScreen(new LoadingScreen());
	}

	private void loadAssets() {
		List<String> textures = new ArrayList<String>(Arrays.asList(new String[]{"Body"}));

		assetManager = new AssetManager();

		for(String texture : textures) {
			assetManager.load("textures/" + texture + ".png", Texture.class);
		}

		//assetManager.load("audio/axeSwing.mp3", Sound.class);

		// only needed once
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("maps/Level1.tmx", TiledMap.class);
	}

	@Override
	public void render() {
		if(isLoading) {
			if (assetManager.update()) {
				setScreen(new GameScreen());
				isLoading = false;
			}
		}
		super.render();
	}

	public TiledMap getMap(String mapName) {
		return assetManager.get("maps/" + mapName + ".tmx", TiledMap.class);

	}

	public Texture getTexture(String texture) {
		return assetManager.get("textures/" + texture + ".png", Texture.class);
	}

	public void playSound(String file) {
		assetManager.get("audio/" + file + ".mp3", Sound.class).play();
	}
}
