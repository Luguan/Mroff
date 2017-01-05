package com.Luguan.Mroff;

import com.Luguan.Mroff.input.GlobalKeybindings;
import com.Luguan.Mroff.screen.GameScreen;
import com.Luguan.Mroff.screen.LoadingScreen;
import com.Luguan.Mroff.screen.MenuScreen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mroff extends Game implements MenuScreen.MenuAction {
    private static final int SPRITE_SIZE = 16;

    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private static Mroff instance;
    private AssetManager assetManager;
    private boolean isLoading = true;
    private boolean isFullscreen = false;
    private int defaultWidth, defaultHeight;
    private NativeWindowManager windowManager;
    private TextureRegion[][] spriteSheet;

    public Mroff(NativeWindowManager windowManager) {
        this.windowManager = windowManager;
        instance = this;
    }

    public static Mroff getInstance() {
        return instance;
    }

    @Override
    public void create() {
        loadAssets();
        setScreen(new LoadingScreen());
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(new GlobalKeybindings());
        defaultWidth = Gdx.graphics.getWidth();
        defaultHeight = Gdx.graphics.getHeight();
    }

    private void loadAssets() {
        List<String> textures = new ArrayList<String>(Arrays.asList(new String[]{"items/Mushroom", "spritesheet"}));

        assetManager = new AssetManager();

        for (String texture : textures) {
            assetManager.load("textures/" + texture + ".png", Texture.class);
        }

        //assetManager.load("audio/axeSwing.mp3", Sound.class);
        assetManager.load("skins/uiskin.json", Skin.class);

        // only needed once
        TmxMapLoader loader = new TmxMapLoader(new InternalFileHandleResolver());
        assetManager.setLoader(TiledMap.class, loader);
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter= Texture.TextureFilter.Nearest;
        assetManager.load("maps/Level2.tmx", TiledMap.class, params);
        assetManager.load("maps/Level1.tmx", TiledMap.class, params);
    }

    @Override
    public void render() {
        if (isLoading) {
            if (assetManager.update()) {
                splitSpriteSheets();
                openMainMenu();
                isLoading = false;
            }
        }

        super.render();
    }

    private void splitSpriteSheets() {
        Texture sheet = getTexture("spritesheet");
        int rows = sheet.getHeight() / SPRITE_SIZE;
        int cols = sheet.getWidth() / SPRITE_SIZE;

        spriteSheet = TextureRegion.split(sheet, sheet.getWidth() / cols, sheet.getHeight() / rows);
    }

    public TiledMap getMap(String mapName) {
        return assetManager.get("maps/" + mapName + ".tmx", TiledMap.class);

    }

    public TextureRegion getSheet(int x, int y) {
        return spriteSheet[x][y];
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
        getScreen().dispose();
        setScreen(new GameScreen());
    }

    public void openMainMenu() {
        getScreen().dispose();
        setScreen(new MenuScreen(this));

    }

    public void goFullscreen() {
        if (windowManager.goFullscreen()) {
            isFullscreen = true;
        }
    }

    public void goWindowMode() {
        if (windowManager.goWindowMode(defaultWidth, defaultHeight)) {
            isFullscreen = false;
        }
    }

    public void toggleFullscreen() {
        if (isFullscreen)
            goWindowMode();
        else
            goFullscreen();
    }


    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }


    public interface NativeWindowManager {
        boolean goFullscreen();

        boolean goWindowMode(int width, int height);
    }
}
