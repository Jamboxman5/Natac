package me.jamboxman5.natac.structures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Structure {

    protected int buildCost;

    protected int revenuePerTurn;
    protected int resourcesPerTurn;

    protected Vector2 tilePos;

    protected Vector2 position;

    protected transient Color drawColor;

    protected String structureName = "Structure";

    protected Structure() {
        this.drawColor = Color.WHITE;
    }

    protected Structure(int buildCost, int revenuePerTurn, int resourcesPerTurn, Vector2 tilePos, Vector2 position, String name) {
        this.buildCost = buildCost;
        this.revenuePerTurn = revenuePerTurn;
        this.resourcesPerTurn = resourcesPerTurn;

        this.tilePos = tilePos;
        this.position = position;
        this.drawColor = Color.WHITE;
        this.structureName = name;
    }

    public abstract void update();

    public void draw(SpriteBatch batch, ShapeDrawer shapes) {
        shapes.setColor(drawColor);
        shapes.filledRectangle(getBounds(tilePos, Natac.instance.getGame().getMap().findTile(tilePos).getCurrentScale()));
    }

    public void drawModal(SpriteBatch batch, ShapeDrawer shapes, Vector2 center) {
        shapes.setColor(drawColor);
        shapes.filledRectangle(getBounds(center, 5f));

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
        return structureName + " ($" + buildCost + ")";
    }

    public int getBuildCost() { return buildCost; }

    public Rectangle getBounds(Vector2 center, float scale) {
        Vector2 drawPos = center.cpy().add(position.cpy().scl(scale));
        drawPos.sub((5f * scale)/2f, (5f * scale)/2f);
        return new Rectangle(drawPos.x, drawPos.y, 5f * scale, 5f * scale);
    }
}
