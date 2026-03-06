package me.jamboxman5.natac.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Unit {
    int speed;
    int range;

    float x;
    float y;

    Tile location;

    protected Unit(int speed, int range, Tile location) {
        this.speed = speed;
        this.range = range;
        this.location = location;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);
}
