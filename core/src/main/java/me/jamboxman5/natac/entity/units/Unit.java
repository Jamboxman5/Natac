package me.jamboxman5.natac.entity.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.packet.PacketMoveUnit;
import me.jamboxman5.natac.net.packet.PacketUtil;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.List;
import java.util.UUID;

public abstract class Unit extends Entity {

    protected int speed;
    protected int range;

    protected Vector2 targetTilePos;
    protected Vector2 homePos;

    protected int travelCounter = 0;

    protected UUID owner;

    protected transient Color color;

    protected transient Entity target;

    protected Unit() {
        color = Color.WHITE;
    }

    protected float alpha = 1f;

    protected UUID id = UUID.randomUUID();

    protected Unit(int speed, int range, Vector2 tilePos, Vector2 position, Color color, UUID owner) {
        this.speed = speed;
        this.range = range;
        this.tilePos = tilePos;
        this.position = position;
        this.homePos = position;
        this.owner = owner;
        this.color = color;
    }

    public void update() {
        if (isTravelling()) {
            int tilePassability = Natac.instance.getGame().getMap().findTile(tilePos).getType().passability;

            if (travelCounter >= tilePassability) {
                travelCounter = 0;
                travel();
            }
            alpha -= .005f;
            if (alpha < .4f) alpha = 1f;
        } else {
            alpha = 1f;
        }

        if (target != null) {
            //move toward current target
            seek(target.getPosition());
        } else {
            //move back to standard position
            if (!position.epsilonEquals(homePos)) {
                seek(homePos);
            }
        }
    }

    public void seek(Vector2 target) {
        Vector2 newPosition = position.cpy();
        newPosition.lerp(target, 0.025f);
        if (newPosition.dst(target) < 1) newPosition = target;
        PacketUtil.repositionUnit(this, newPosition);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale) {
        Color drawColor = new Color(color);
        drawColor.a = alpha;
        shapes.setColor(drawColor);
        shapes.filledCircle(center.cpy().add(position.cpy().scl(scale)), 5 * scale);
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
            float distance =  targetTilePos.dst(t.getTilePosition());
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closest = t;
            }
        }

        if (closest == null) return;

        PacketUtil.moveUnit(this, closest.getTilePosition(), current.getTilePosition());

    }

    public void incrementTravel() {
        travelCounter++;
    }

    public void move(Tile from, Tile to) {
        from.removeUnit(this);
        to.addUnit(this);
        tilePos = to.getTilePosition();
        Vector2 displacement =
            from.getTilePosition().cpy()
                .sub(to.getTilePosition());
        position.add(displacement);
        to.defog();

        if (tilePos.epsilonEquals(targetTilePos)) targetTilePos = null;
    }

    public UUID getID() { return id; }

    public Entity getTarget() { return target; }
    public void setTarget(Entity newTarget) { target = newTarget; }

}
