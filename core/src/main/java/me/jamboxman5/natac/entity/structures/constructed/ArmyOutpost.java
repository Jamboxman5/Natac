package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class ArmyOutpost extends Structure {

    public static final String name = "ArmyOutpost";
    public static final int goldCost = 0;
    public static final int resourceCost = 100;

    public ArmyOutpost() {
        this.drawColor = Color.GOLD;
        this.structureName = name;
    }

    public ArmyOutpost(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(goldCost, 0, 0, 100, tilePos, pos, name);
    }

    @Override
    public void update() {

    }

}
