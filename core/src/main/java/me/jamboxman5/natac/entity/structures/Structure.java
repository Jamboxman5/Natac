package me.jamboxman5.natac.entity.structures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Structure extends Entity {

    protected int goldCost;
    protected int resourceCost;

    protected int revenuePerTurn;
    protected int resourcesPerTurn;

    protected transient Color drawColor;
    protected transient Sprite sprite;

    protected String structureName = "Structure";

    private final static float structureScale = .75f;

    protected float spriteYOffset = 1;
    protected float spriteScale = 1;

    protected Structure() {
        this.drawColor = Color.WHITE;
        spriteYOffset = 0;
    }

    protected Structure(int goldCost, int resourceCost, int revenuePerTurn, int resourcesPerTurn,
                        int maxHealth, Vector2 tilePos, Vector2 position,
                        String name) {
        super(position, tilePos, new Rectangle(tilePos.x + position.x - 5, tilePos.y + position.y - 5, 10, 10), maxHealth);
        this.goldCost = goldCost;
        this.resourceCost = resourceCost;
        this.revenuePerTurn = revenuePerTurn;
        this.resourcesPerTurn = resourcesPerTurn;

        this.drawColor = Color.WHITE;
        this.structureName = name;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale) {
        Vector2 drawPos = getDrawPos(center, scale);
        shapes.setColor(new Color(0f, 0f, 0f, .25f));
        if (sprite != null) {
            shapes.filledEllipse(drawPos.x, drawPos.y + (spriteYOffset * spriteScale * scale * structureScale), (sprite.getWidth()/2f) * scale * spriteScale * structureScale, 5 * scale * structureScale);
            sprite.setScale(scale * structureScale * spriteScale);
            sprite.setOrigin(sprite.getWidth()/2f, 0f);
            sprite.setOriginBasedPosition(drawPos.x, drawPos.y + (spriteYOffset * scale * structureScale));
            sprite.draw(batch);
            if (Settings.debugMode) {
                shapes.setColor(Color.RED);
                shapes.setDefaultLineWidth(1f);
                shapes.rectangle(getBounds(center, scale));
            }
            return;
        }
        Rectangle bounds = getBounds(center, scale);
        shapes.filledEllipse(drawPos.x, drawPos.y, (bounds.getWidth()), 5 * scale * structureScale);
        shapes.setColor(drawColor);
        shapes.filledRectangle(getBounds(center, scale));

        if (Settings.debugMode) {
            shapes.setColor(Color.RED);
            shapes.setDefaultLineWidth(1f);
            shapes.rectangle(getBounds(center, scale));
        }

    }

    protected static Vector2 getRandomPosition() {
        float xDiff = (float) (Math.random() * 50f);
        float yDiff = (float) (Math.random() * 50f);
        if (Math.random() > .5) xDiff = -xDiff;
        if (Math.random() > .5) yDiff = -yDiff;
        return new Vector2(xDiff, yDiff);
    }

    public int getRevenuePerTurn() { return revenuePerTurn; }
    public int getResourcesPerTurn() { return resourcesPerTurn; }

    public String toString() {
        String s = structureName;
        if (goldCost > 0) s += " ($" + goldCost + ")";
        if (resourceCost > 0) s += " (" + resourceCost + "R)";
        return s;
    }

    public int getGoldCost() { return goldCost; }
    public int getResourceCost() { return resourceCost; }

    public Rectangle getBounds(Vector2 center, float scale) {
        Vector2 drawPos = getDrawPos(center, scale);
        drawPos.sub((5f * scale)/2f, (5f * scale)/2f);
        return new Rectangle(drawPos.x, drawPos.y, 5f * scale, 5f * scale);
    }

    protected Vector2 getDrawPos(Vector2 center, float scale) {
        return center.cpy().add(position.cpy().scl(scale));
    }

}
