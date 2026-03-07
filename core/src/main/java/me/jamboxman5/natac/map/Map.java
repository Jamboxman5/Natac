package me.jamboxman5.natac.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileState;
import me.jamboxman5.natac.units.army.Soldier;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Tile> tiles;

    public Map() {
        tiles = new ArrayList<>();

    }

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes) {
        for (Tile t : tiles) t.draw(camera, batch, shapes);
    }

    public void update(Vector2 touchPos) {
        for (Tile t : tiles) t.update(touchPos);
    }

    public void clickTile(Vector2 pos) {
        for (Tile t : tiles) {
            if (t.contains(pos) && t.getState() == TileState.SELECTABLE) t.addUnit(new Soldier(t));
//            else if (t.contains(pos) && t.getState() == Tile.TileState.SELECTED) t.setState(Tile.TileState.SELECTABLE);
        }
    }


    public void addTile(Tile t) { tiles.add(t); }

}
