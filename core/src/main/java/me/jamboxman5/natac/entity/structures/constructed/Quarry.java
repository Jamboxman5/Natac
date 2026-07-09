package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class Quarry extends Structure {

    public static final String name = "Quarry";
    public static final int goldCost = 0;
    public static final int resourceCost = 30;

    public static final String spritePath = "structure/constructed/quarry.png";

    public Quarry() {
        this.drawColor = Color.GRAY;
        this.structureName = name;
    }

    public Quarry(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, resourceCost, 10, 0, 30, tilePos, pos, name);
        initGraphics(new Texture(Gdx.files.internal("structure/placeholder_structure.png")), 0, 1);
    }

    @Override
    public void update() {
        if (sprite == null) {
            initGraphics(new Texture(Gdx.files.internal(spritePath)), 0, 1);
        }
    }

}
