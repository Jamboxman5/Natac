package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class ScoutTower extends Structure {

    public static final String name = "Scout Tower";
    public static final int goldCost = 0;
    public static final int resourceCost = 40;

    public ScoutTower() {
        this.drawColor = Color.ORANGE;
        this.structureName = name;
    }

    public ScoutTower(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, resourceCost, 0, 0, 20, tilePos, pos, name);
    }

    @Override
    public void update() {

    }

}
