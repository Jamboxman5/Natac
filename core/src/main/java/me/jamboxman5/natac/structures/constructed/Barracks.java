package me.jamboxman5.natac.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.structures.Structure;

public class Barracks extends Structure {

    public Barracks() {
        this.drawColor = Color.CHARTREUSE;
    }

    public Barracks(PlayerClass playerClass, Vector2 tilePos, Vector2 pos) {
        super(50, 0, 0, tilePos, pos);
    }

    @Override
    public void update() {

    }

}
