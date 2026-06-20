package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

// Leaving this as just one building for both caravans and flying machines

public class Depot extends Structure {

    public static final String name = "Depot";
    public static final int goldCost = 100;
    public static final int resourceCost = 0;

    public Depot() {
        this.drawColor = Color.BLUE;
        this.structureName = name;
    }

    public Depot(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, 0, 0, 50, tilePos, pos, name);
    }

    @Override
    public void update() {

    }

}
