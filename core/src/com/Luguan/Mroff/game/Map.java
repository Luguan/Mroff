package com.Luguan.Mroff.game;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Map {
    private TiledMap tiledMap;

    public Map(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public MapLayers getLayers() {
        return tiledMap.getLayers();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
