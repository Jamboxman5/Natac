package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Color;

public enum TileState {
    HIDDEN(Color.BLACK),
    BLOCKED(new Color(0, 0, 0f, 0f)),
    SELECTABLE(new Color(0f, 0f, .6f, .5f)),
    SELECTED(new Color(0, 0, .4f, .7f)),
    STARTING(new Color(0f, 0f, .6f, .5f)),
    CLAIMED(new Color(0, .5f, 0f, .5f)),
    ENEMY_CLAIMED(new Color(0.6f, 0f, 0f, .7f));

    public final Color tileColor;

    TileState(Color tileColor) {
        this.tileColor = tileColor;
    }
}
