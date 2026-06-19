package me.jamboxman5.natac.entity.structures.generated;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.structures.Structure;

public class Ruins extends Structure {

    public Ruins() {
        this.drawColor = Color.BROWN;
    }

    public Ruins(Vector2 tilePos) {
        super(1, 1, 1, (int) Float.POSITIVE_INFINITY, tilePos, getRandomPosition(), "Ruins");
    }

    @Override
    public void update() {

    }

}
