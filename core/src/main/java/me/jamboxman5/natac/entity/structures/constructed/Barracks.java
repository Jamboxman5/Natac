package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class Barracks extends Structure {

    public static final String name = "Barracks";
    public static final int goldCost = 50;
    public static final int resourceCost = 0;

    public Barracks() {
        this.drawColor = Color.CHARTREUSE;
        this.structureName = name;
    }

    public Barracks(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, resourceCost, 0, 0, 50, tilePos, pos, name);
    }

    @Override
    public void update() {
        if (sprite == null) {
            initGraphics(new Texture(Gdx.files.internal("structure/placeholder_structure.png")), 0, 1);
        }
    }

}
