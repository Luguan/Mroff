package com.Luguan.Mroff;

import com.Luguan.Mroff.screen.GameScreen;
import com.Luguan.Mroff.screen.LoadingScreen;
import com.Luguan.Mroff.screen.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mroff extends Game implements MenuScreen.MenuAction {

	private static Mroff instance;
	private AssetManager assetManager;
	private boolean isLoading = true;

	public Mroff() {
		instance = this;
	}

	public static Mroff getInstance() {
		return instance;
	}

	@Override
	public void create() {
		loadAssets();
		setScreen(new LoadingScreen());
	}

	private void loadAssets() {
		List<String> textures = new ArrayList<String>(Arrays.asList(new String[]{"Body"}));

		assetManager = new AssetManager();

		for (String texture : textures) {
			assetManager.load("textures/" + texture + ".png", Texture.class);
		}

		//assetManager.load("audio/axeSwing.mp3", Sound.class);
		assetManager.load("skins/uiskin.json", Skin.class);

		// only needed once
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load("maps/Level1.tmx", TiledMap.class);
	}

	@Override
	public void render() {
		if (isLoading) {
			if (assetManager.update()) {
				setScreen(new MenuScreen(this));
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

	public Skin getSkin() {
		return assetManager.get("skins/uiskin.json");
	}

	public void playSound(String file) {
		assetManager.get("audio/" + file + ".mp3", Sound.class).play();
	}

	@Override
	public void newGame() {
		setScreen(new GameScreen());
	}
}
