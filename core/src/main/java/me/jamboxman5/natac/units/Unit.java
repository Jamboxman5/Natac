package me.jamboxman5.natac.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Unit {
    protected int speed;
    protected int range;

    protected Vector2 position;

    protected Tile location;

    protected Unit(int speed, int range, Tile location) {
        this.speed = speed;
        this.range = range;
        this.location = location;
        this.position = location.getTilePosition();
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);
}
