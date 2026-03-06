package me.jamboxman5.natac.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Unit {
    int speed;
    int range;

    protected Unit(int speed, int range) {
        this.speed = speed;
        this.range = range;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);
}
