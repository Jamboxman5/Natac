package me.jamboxman5.natac.map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileState;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.GameScreen;
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
//        if (Natac.instance.getGame().getState() != GameScreen.State.CLAIM) {
//            for (Tile t : tiles)
//                if (t.getState() == TileState.SELECTABLE) t.setState(TileState.BLOCKED);
//        }
    }

    public void clearStartingTiles() {
        for (Tile t : tiles) {
            if (t.getState() == TileState.STARTING) t.setState(TileState.BLOCKED);
        }
    }

    public void clickTile(Vector2 pos) {
        for (Tile t : tiles) {
            if (t.contains(pos)) {
                switch (t.getState()) {
                    case STARTING:
                        t.claim(Natac.instance.player.getID(), true);
                        clearStartingTiles();
                        Natac.instance.endTurn();
                        return;
                    default:
                        return;

                }
            }

        }
    }

    public Tile findTile(Vector2 pos) {
        for (Tile t : tiles) {
            if (t.contains(pos)) return t;
        }
        return null;
    }

    public List<Tile> getNeighbors(Tile t) {
        List<Tile> neighbors = new ArrayList<>();
        Vector2 pos = t.getTilePosition();

        Tile candidate;

        candidate = findTile(pos.cpy().add(150, 75));
        if (candidate != null) neighbors.add(candidate);

        candidate = findTile(pos.cpy().add(-150, 75));
        if (candidate != null) neighbors.add(candidate);

        candidate = findTile(pos.cpy().add(0, 100));
        if (candidate != null) neighbors.add(candidate);

        candidate = findTile(pos.cpy().add(0, -100));
        if (candidate != null) neighbors.add(candidate);

        candidate = findTile(pos.cpy().add(-150, -75));
        if (candidate != null) neighbors.add(candidate);

        candidate = findTile(pos.cpy().add(150, -75));
        if (candidate != null) neighbors.add(candidate);

        return neighbors;

    }


    public void addTile(Tile t) { tiles.add(t); }

    public boolean hasTiles(Player player) {
        for (Tile t : tiles) {
            if (t.getOwner() == null) continue;
            if (t.getOwner().equals(player.getID())) return true;
        }
        return false;
    }
}
