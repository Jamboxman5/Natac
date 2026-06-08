package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Color;

public enum TileState {
    UNAVAILABLE(Color.BLACK),
    BLOCKED(new Color(1f, 1f, 1f, 1f)),
    SELECTABLE(new Color(0f, 0f, .6f, 1f)),
    SELECTED(new Color(0, 0, .4f, 1f)),
    STARTING(new Color(0f, 0f, 1f, 1f)),
    CLAIMED(new Color(.4f, 1f, .4f, 1f)),
    ENEMY_CLAIMED(new Color(0.6f, 0f, 0f, 1f));

    public final Color highlightColor;

    TileState(Color highlightColor) {
        this.highlightColor = highlightColor;
    }
}
