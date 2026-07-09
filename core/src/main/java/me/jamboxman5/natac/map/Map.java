package me.jamboxman5.natac.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.map.tile.NeighborSet;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileState;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.units.Unit;
import me.jamboxman5.natac.sfx.Sounds;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Map {
    private List<Tile> tiles;
    private int defogRadius = Settings.defogTileRadius;

    private transient Tile selectedTile = null;

    public Map() {
        tiles = new ArrayList<>();

    }

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes, GameScreen.SelectionMode tileSelectState) {
        for (Tile t : tiles) t.draw(camera, batch, shapes, tileSelectState);
    }

    public void update(Vector2 touchPos) {
        for (Tile t : tiles) t.update(touchPos);
//        if (Natac.instance.getGame().getState() != GameScreen.State.CLAIM) {
//            for (Tile t : tiles)
//                if (t.getState() == TileState.SELECTABLE) t.setState(TileState.BLOCKED);
//        }
    }

    public void sortTiles() {
        tiles.sort(new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                float y1 = o1.getTilePosition().y * 10;
                float y2 = o2.getTilePosition().y * 10;
                return (int) (y2-y1);            }
        });
    }

    public void update() {
        for (Tile t : tiles) {
            t.update();
            if (t.battlePending()) {
                Natac.instance.getGame().startBattle(t);
//                PacketUtil.startBattle(t.getTilePosition(), t.getUnitOwners());
            }
        }
    }

    public void clearStartingTiles() {
        for (Tile t : tiles) {
            if (t.getState() == TileState.STARTING) t.setState(TileState.BLOCKED);
        }
    }

    public boolean ownsTiles(Player owner) {
        for (Tile t : tiles) {
            if (t.getOwner() != null && t.getOwner().equals(owner.getID())) return true;
        }
        return false;
    }

    public List<Tile> getTiles(Player owner) {
        List<Tile> owned = new ArrayList<>();
        for (Tile t : tiles) {
            if (t.getOwner() != null && t.getOwner().equals(owner.getID())) owned.add(t);
        }
        return owned;
    }

    public List<Tile> getBaseTiles() {
        List<Tile> base = new ArrayList<>();
        for (Tile t : tiles) {
            if (getNeighborSet(t).size() <= 3 && t.getOwner() == null) {
                base.add(t);
            }
        }
        return base;
    }

    public void clickTile(Vector2 pos) {

        for (Tile t : tiles) {
            if (t.contains(pos)) {

                GameScreen gameScreen = Natac.instance.getGame();

                if (gameScreen.getTileSelectionMode() == GameScreen.SelectionMode.BASE && gameScreen.getGameState() != GameScreen.State.WAIT) {

                    switch (t.getState()) {
                        case STARTING:
                            t.claim(Natac.instance.player.getID(), true);
                            clearStartingTiles();
                            Natac.instance.endTurn();
                            gameScreen.setTileSelectionMode(GameScreen.SelectionMode.NONE);
                            return;
                        default:
                            return;

                    }

                } else if (gameScreen.getTileSelectionMode() == GameScreen.SelectionMode.TRAVEL) {

                   if (t.getState() != TileState.ENEMY_CLAIMED && !Natac.instance.getGame().getSelectedUnit().getTilePosition().epsilonEquals(t.getTilePosition())) {
                       Sounds.SELECT.play();
                       gameScreen.setTileSelectionMode(GameScreen.SelectionMode.NONE);
                       gameScreen.getSelectedUnit().deploy(t);
                   }

                } else if (gameScreen.getTileSelectionMode() == GameScreen.SelectionMode.NONE) {

                    if (t.getState() == TileState.CLAIMED || t.hasUnits(Natac.instance.player)) {
                        Sounds.SELECT.play();
                        selectedTile = t;
                    }

                }

                return;

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
        return new ArrayList<>(new NeighborSet(t, this).getNeighbors());
    }

    public NeighborSet getNeighborSet(Tile t) {
        return new NeighborSet(t, this);
    }

    public void addTile(Tile t) { tiles.add(t); }

    public boolean hasTiles(Player player) {
        for (Tile t : tiles) {
            if (t.getOwner() == null) continue;
            if (t.getOwner().equals(player.getID())) return true;
        }
        return false;
    }

    public int getDefogTileRadius() { return defogRadius;
    }

    public boolean isSelectedTile(Tile tile) { return selectedTile == tile; }

    public Tile getSelectedTile() { return selectedTile; }

    public void deselectTile() {selectedTile = null;}

    public void collectRevenues() {
        int resourcesCollected = 0;
        int goldCollected = 0;
        for (Tile t : tiles) {
            if (t.getOwner() == null) continue;
            if (!t.getOwner().equals(Natac.instance.player.getID())) continue;
            for (Structure s : t.getStructures()) {
                resourcesCollected += s.getResourcesPerTurn();
                goldCollected += s.getRevenuePerTurn();
            }
        }
        PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, goldCollected, resourcesCollected);
        if (goldCollected > 0 || resourcesCollected > 0) Sounds.RECEIVE_GOLD.play();
    }

    public void updateTravellers() {
        for (Tile t : tiles) {
            if (t.getState() == TileState.UNAVAILABLE) continue;
            if (t.getUnits() == null) continue;
            if (t.getUnits().isEmpty()) continue;

            for (Unit u : t.getUnits()) {
                if (u.isTravelling()) u.incrementTravel();
            }

        }
    }

    public void setupBaseTiles() {
        for (Tile t : tiles) {
            if (getNeighborSet(t).size() <= 3) {
                t.setState(TileState.STARTING);
                t.clearStructures();
            }
        }
    }

    public Unit findUnit(UUID unitID) {
        for (Tile t : tiles) {
            for (Unit u : t.getUnits()) {
                if (u.getID().equals(unitID)) return u;
            }
        }
        return null;
    }

    public Entity findEntity(UUID entityID) {
        for (Tile t : tiles) {
            for (Entity e : t.getEntities()) {
                if (e.getID().equals(entityID)) return e;
            }
        }
        return null;
    }
}
