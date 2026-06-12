package me.jamboxman5.natac.structures.prop;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.structures.Structure;

public class Prop extends Structure {

    public Prop() {}

    public Prop(Vector2 pos, Vector2 tilePos, String name) {
        super(0, 0, 0, tilePos, pos, name);
    }

    @Override
    public void update() {

    }

}
