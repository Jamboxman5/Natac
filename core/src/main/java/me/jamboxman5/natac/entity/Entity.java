package me.jamboxman5.natac.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.UUID;

public abstract class Entity {

    protected Vector2 position;
    protected Vector2 tilePos;

    protected int health;
    protected int defense;
    protected int maxHealth;

    protected Rectangle collisionBox;

    protected UUID id = UUID.randomUUID();

    protected Entity() {
        this(new Vector2(), new Vector2(), new Rectangle(0, 0, 10, 10), 100);
    }

    protected Entity(Vector2 position, Vector2 tilePos, Rectangle collisionBox, int maxHealth) {
        this.position = position.cpy();
        this.tilePos = tilePos.cpy();
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.collisionBox = collisionBox;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale);

    public Vector2 getPosition() {
        return position;
    }
    public Vector2 getTilePosition() { return tilePos; }


    public void setPosition(Vector2 newPosition) { this.position = newPosition; }

    public void damage(int damagePts, Vector2 displacement) {
        health -= damagePts;
        position.add(displacement);
        System.out.println(health + " (-" + damagePts + ")");
    }
    public boolean isDestroyed() { return health <= 0; }

    public UUID getID() { return id; }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Rectangle getBounds(Vector2 center, float scale) {
        Rectangle rect = new Rectangle(collisionBox);

        // Convert tile-relative position to draw position
        float cx = center.x + position.x * scale;
        float cy = center.y + position.y * scale;

        // Scale dimensions
        float scaledWidth = rect.width * scale;
        float scaledHeight = rect.height * scale;

        // Re-center rectangle
        rect.set(
            cx - scaledWidth / 2f,
            cy - scaledHeight / 2f,
            scaledWidth,
            scaledHeight
        );

        return rect;
    }

}
