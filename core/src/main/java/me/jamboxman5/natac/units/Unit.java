package me.jamboxman5.natac.units;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.UUID;

public abstract class Unit {
    protected int speed;
    protected int range;

    protected Vector2 position;

    protected Vector2 tilePos;

    protected UUID owner;

    protected Unit() {}

    protected Unit(int speed, int range, Vector2 tilePos, Vector2 position, UUID owner) {
        this.speed = speed;
        this.range = range;
        this.tilePos = tilePos;
        this.position = position;
        this.owner = owner;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);

    public UUID getOwner() { return owner; }
}
