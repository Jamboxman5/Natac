package me.jamboxman5.natac.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Tile> tiles;

    public Map() {
        tiles = new ArrayList<>();

    }

    public void draw(Camera camera, SpriteBatch batch, ShapeRenderer shapes) {
        for (Tile t : tiles) t.draw(camera, batch, shapes);
    }

    public void update(Vector2 touchPos) {
        for (Tile t : tiles) t.update(touchPos);
    }

    public void addTile(Tile t) { tiles.add(t); }

}
