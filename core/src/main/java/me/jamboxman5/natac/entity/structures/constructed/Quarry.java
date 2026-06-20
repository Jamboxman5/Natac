package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class Quarry extends Structure {

    public static final String name = "Quarry";
    public static final int goldCost = 0;
    public static final int resourceCost = 30;

    public Quarry() {
        this.drawColor = Color.GRAY;
        this.structureName = name;
    }

    public Quarry(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, resourceCost, 10, 0, 30, tilePos, pos, name);
    }

    @Override
    public void update() {

    }

}
