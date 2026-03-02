package me.jamboxman5.natac.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.map.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Tile> tiles;

    public Map() {
        tiles = new ArrayList<>();
    }

    public void draw(SpriteBatch batch) {
        for (Tile t : tiles) t.draw(batch);
    }


}
