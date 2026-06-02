package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum TileType {
    PLAINS(new Color(.2f, 0.8f, .2f, 1f), 1, new Texture(Gdx.files.internal("tile/ForestsTileSprite.png"))),
    FOREST(new Color(.2f, 1f, .2f, 1f), 3, new Texture(Gdx.files.internal("tile/MountainTileSprite_1.png"))),
    MOUNTAINS(new Color(.4f, .2f, .2f, 1f), 6, new Texture(Gdx.files.internal("tile/TileSpriteBase.png")));

    public final Color tileColor;
    public final int passability;
    public final Texture texture;

    TileType(Color tileColor, int passability, Texture texture) {
        this.tileColor = tileColor;
        this.passability = passability;
        this.texture = texture;
    }
}
