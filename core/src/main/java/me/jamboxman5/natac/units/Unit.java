package me.jamboxman5.natac.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.List;
import java.util.UUID;

public abstract class Unit {
    protected int speed;
    protected int range;

    protected Vector2 position;

    protected Vector2 tilePos;

    protected Vector2 targetTilePos;
    protected int travelCounter = 0;

    protected UUID owner;

    protected transient Color color;

    protected Unit() {
        color = Color.WHITE;
    }

    protected Unit(int speed, int range, Vector2 tilePos, Vector2 position, Color color, UUID owner) {
        this.speed = speed;
        this.range = range;
        this.tilePos = tilePos;
        this.position = position;
        this.owner = owner;
        this.color = color;
    }

    public void update() {
        if (isTravelling()) {
            int tilePassability = Natac.instance.getGame().getMap().findTile(tilePos).getType().passability;
            System.out.println("TRAVELLING: " + travelCounter + " | " + tilePassability);

            if (travelCounter >= tilePassability) {
                travelCounter = 0;
                travel();
            }
        }
    }

    public void draw(SpriteBatch batch, ShapeDrawer shapes) {
        shapes.setColor(color);
        shapes.filledCircle(tilePos.cpy().add(position.cpy().scl(Natac.instance.getGame().getMap().findTile(tilePos).getCurrentScale())), 5);
    }

    public void drawModal(SpriteBatch batch, ShapeDrawer shapes, Vector2 center) {
        shapes.setColor(color);
        shapes.filledCircle(center.cpy().add(position.cpy().scl(5)), 25);
    }


    public UUID getOwner() { return owner; }

    public Circle getBounds(Vector2 center, float scale) {
        Vector2 drawPos = center.cpy().add(position.cpy().scl(scale));
        return new Circle(drawPos, 5f * scale);
    }

    public void deploy(Tile target) {
        targetTilePos = target.getTilePosition();
    }

    public boolean isTravelling() {
        return (targetTilePos != null && !targetTilePos.epsilonEquals(tilePos));
    }

    protected void travel() {
        Tile current = Natac.instance.getGame().getMap().findTile(tilePos);
        List<Tile> candidates = current.getNeighbors();

        Tile closest = null;
        float shortestDistance = Float.POSITIVE_INFINITY;

        for (Tile t : candidates) {
            float distance = targetTilePos.cpy().sub(tilePos).len();
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closest = t;
            }
        }

        if (closest == null) return;

        current.removeUnit(this);
        closest.addUnit(this);
        tilePos = closest.getTilePosition();

        if (tilePos.epsilonEquals(targetTilePos)) targetTilePos = null;

    }

    public void incrementTravel() {
        travelCounter++;
    }

}
