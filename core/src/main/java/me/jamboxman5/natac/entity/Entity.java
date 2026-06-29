package me.jamboxman5.natac.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.UUID;

public abstract class Entity {

    protected Vector2 position;
    protected Vector2 tilePos;

    protected int health;
    protected int defense;
    protected int maxHealth;

    protected Rectangle collisionBox;
    protected transient Sprite sprite;

    protected float spriteYOffset = 0;
    protected float spriteScale = 1;
    protected float scaleMod = 1;

    protected transient Color drawColor;

    protected UUID id = UUID.randomUUID();

    protected Entity() {
        this(new Vector2(), new Vector2(), new Rectangle(0, 0, 10, 10), 100, 1);
    }

    protected Entity(Vector2 position, Vector2 tilePos, Rectangle collisionBox, int maxHealth, float scaleMod) {
        this.position = position.cpy();
        this.tilePos = tilePos.cpy();
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.collisionBox = collisionBox;
        this.scaleMod = scaleMod;

    }

    public abstract void update();

    public void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale) {

        if (sprite == null) return;

        Vector2 drawPos = getDrawPos(center, scale);
        shapes.setColor(new Color(0f, 0f, 0f, .25f));

        shapes.filledEllipse(drawPos.x, drawPos.y + (spriteYOffset * spriteScale * scale), (sprite.getWidth()/2f) * scale * spriteScale, 5 * scale);
        sprite.setScale(scale * spriteScale * scaleMod);
        sprite.setOrigin(sprite.getWidth()/2f, 0f);
        sprite.setOriginBasedPosition(drawPos.x, drawPos.y + (spriteYOffset * scale));
        sprite.draw(batch);
        if (Settings.debugMode) {
            drawCollision(shapes, drawPos, scale * spriteScale);
            shapes.setColor(Color.WHITE);
            shapes.filledCircle(drawPos.x, drawPos.y, 1);
        }

    }
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

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    protected Vector2 getDrawPos(Vector2 center, float scale) {
        return center.cpy().add(position.cpy().scl(scale));
    }

    protected void drawCollision(ShapeDrawer shapes, Vector2 drawPos, float scale) {
        shapes.setColor(Color.RED);
        shapes.setDefaultLineWidth(1f);
        float w = collisionBox.width * scale * scaleMod;
        shapes.rectangle(drawPos.x - (w / 2f), drawPos.y, w, collisionBox.height * scale * scaleMod);
    }

    public Rectangle getBounds(Vector2 center, float scale) {
        Vector2 drawPos = getDrawPos(center, scale);
        float w = collisionBox.width * scale * scaleMod;
        return new Rectangle(drawPos.x - (w / 2f), drawPos.y, w, collisionBox.height * scale * scaleMod);
    }

    protected void initGraphics(Texture texture, float spriteYOffset, float spriteScale) {
        sprite = new Sprite(texture);
        this.spriteScale = spriteScale;
        this.spriteYOffset = spriteYOffset;
        collisionBox = generateCollisionBox();

    }

    protected Rectangle generateCollisionBox() {
        if (sprite == null) return new Rectangle(position.x - 5, position.y - 5, 10, 10);
        float w = sprite.getWidth();
        float h;
        if (this instanceof Structure) {
            h = sprite.getHeight() / 2f;
        } else {
            h = sprite.getHeight();
        }
        float x = position.x - (w/2f);
        float y = position.y;
        return new Rectangle(x, y, w, h);
    }

}
