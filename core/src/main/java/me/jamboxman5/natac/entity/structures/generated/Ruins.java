package me.jamboxman5.natac.entity.structures.generated;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.structures.Structure;

public class Ruins extends Structure {

    public Ruins() {
        this.drawColor = Color.BROWN;
    }

    public Ruins(Vector2 tilePos) {
        super(0, 0, 0, 0, (int) Float.POSITIVE_INFINITY, tilePos, getRandomPosition(), "Ruins");
        initGraphics(new Texture(Gdx.files.internal("structure/placeholder_structure.png")), 0, 1);

    }

    @Override
    public void update() {
        if (sprite == null) {
            initGraphics(new Texture(Gdx.files.internal("structure/placeholder_structure.png")), 0, 1);
        }
    }

}
