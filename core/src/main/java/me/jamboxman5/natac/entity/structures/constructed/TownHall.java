package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class TownHall extends Structure {

    protected PlayerClass type;

    public TownHall() {
        this.drawColor = Color.PINK;
        this.structureName = "Town Hall";
    }

    public TownHall(PlayerClass playerClass, Vector2 tilePos) {
        super(0, 50, 0, 100, tilePos, new Vector2(0, 0), "Town Hall");
        this.type = playerClass;
    }

    @Override
    public void update() {

    }

}
