package me.jamboxman5.natac.map.tile;

public class TileBuilder {

    public static Tile generateTile() {
        return new Tile(200, 200, Tile.TileState.BLOCKED);
    }
}
