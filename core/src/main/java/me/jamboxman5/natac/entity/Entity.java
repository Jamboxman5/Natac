package me.jamboxman5.natac.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.UUID;

public abstract class Entity {

    protected Vector2 position;
    protected Vector2 tilePos;

    protected int health;
    protected int maxHealth;

    protected UUID id = UUID.randomUUID();

    protected Entity() {
        this(new Vector2(), new Vector2(), 100);
    }

    protected Entity(Vector2 position, Vector2 tilePos, int maxHealth) {
        this.position = position;
        this.tilePos = tilePos;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale);

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getTilePosition() { return tilePos; }


    public void setPosition(Vector2 newPosition) { this.position = newPosition; }

    public void damage(int damagePts) {
        health -= damagePts;
        System.out.println(health + " (-" + damagePts + ")");
    }
    public boolean isDestroyed() { return health <= 0; }

    public UUID getID() { return id; }

}
