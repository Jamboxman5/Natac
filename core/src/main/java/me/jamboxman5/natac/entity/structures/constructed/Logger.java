package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class Logger extends Structure {

    public static final String name = "Logger";
    public static final int goldCost = 30;
    public static final int resourceCost = 0;

    public Logger() {
        this.drawColor = Color.BROWN;
        this.structureName = name;
    }

    public Logger(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, resourceCost, 0, 10, 30, tilePos, pos, name);
    }

    @Override
    public void update() {

    }

}
