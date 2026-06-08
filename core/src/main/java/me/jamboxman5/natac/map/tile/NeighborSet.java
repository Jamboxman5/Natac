package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.Map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class NeighborSet {

    private HashMap<TileNeighbor, Tile> neighborLocations = new HashMap<>();

    public NeighborSet(Tile center, Map map) {
        Vector2 pos = center.getTilePosition();

        Tile candidate;

        candidate = map.findTile(pos.cpy().add(150, 75));
        if (candidate != null) neighborLocations.put(TileNeighbor.UP_RIGHT, candidate);

        candidate = map.findTile(pos.cpy().add(-150, 75));
        if (candidate != null) neighborLocations.put(TileNeighbor.UP_LEFT, candidate);

        candidate = map.findTile(pos.cpy().add(0, 100));
        if (candidate != null) neighborLocations.put(TileNeighbor.UP, candidate);

        candidate = map.findTile(pos.cpy().add(0, -100));
        if (candidate != null) neighborLocations.put(TileNeighbor.DOWN, candidate);

        candidate = map.findTile(pos.cpy().add(-150, -75));
        if (candidate != null) neighborLocations.put(TileNeighbor.DOWN_LEFT, candidate);

        candidate = map.findTile(pos.cpy().add(150, -75));
        if (candidate != null) neighborLocations.put(TileNeighbor.DOWN_RIGHT, candidate);
    }

    public boolean hasNeighbor(TileNeighbor nPos) { return neighborLocations.containsKey(nPos); }
    public Tile getUpNeighbor(TileNeighbor nPos) { return neighborLocations.get(nPos); }

    public Collection<Tile> getNeighbors() { return neighborLocations.values(); }
    public boolean isFull() { return neighborLocations.size() == 6; }
    public boolean isEmpty() { return neighborLocations.isEmpty(); }
    public int size() { return neighborLocations.size(); }
}
