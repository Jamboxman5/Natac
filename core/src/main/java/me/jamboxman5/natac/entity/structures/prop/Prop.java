package me.jamboxman5.natac.entity.structures.prop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.structures.Structure;

public class Prop extends Structure {

    public Prop() { this.drawColor = Color.WHITE; }

    public Prop(Vector2 pos, Vector2 tilePos, String name) {
        super(0, 0, 0, (int) Float.POSITIVE_INFINITY, tilePos, pos, name);
        this.drawColor = Color.WHITE;
    }

    @Override
    public void update() {

    }

}
