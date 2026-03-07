package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Color;

public enum TileState {
    HIDDEN(Color.BLACK),
    BLOCKED(new Color(0, 0, .2f, .5f)),
    SELECTABLE(new Color(.4f, .4f, .4f, .5f)),
    SELECTED(new Color(0, 0, .4f, .7f));

    public final Color tileColor;

    TileState(Color tileColor) {
        this.tileColor = tileColor;
    }
}
