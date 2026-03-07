package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Color;

public enum TileType {
    PLAINS(new Color(.2f, 0.8f, .2f, 1f), 1),
    FOREST(new Color(.2f, 1f, .2f, 1f), 3),
    RADIATION(new Color(.4f, .2f, .2f, 1f), 10);

    public final Color tileColor;
    public final int passability;

    TileType(Color tileColor, int passability) {
        this.tileColor = tileColor;
        this.passability = passability;
    }
}
