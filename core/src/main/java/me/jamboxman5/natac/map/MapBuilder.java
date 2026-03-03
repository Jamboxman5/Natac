package me.jamboxman5.natac.map;

import me.jamboxman5.natac.map.tile.Tile;

public class MapBuilder {
    public static Map generateMap(int radius) {

        Map map = new Map();

        for (int i = 0; i < 5; i++ ) map.addTile(new Tile());

        return map;
    }
}
