package me.jamboxman5.natac.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Entity {

    protected Vector2 position;
    protected Vector2 tilePos;

    protected Entity() {
        this(new Vector2(), new Vector2());
    }

    protected Entity(Vector2 position, Vector2 tilePos) {
        this.position = position;
        this.tilePos = tilePos;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale);

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getTilePosition() { return tilePos; }


    public void setPosition(Vector2 newPosition) { this.position = newPosition; }
}
