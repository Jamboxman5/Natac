package me.jamboxman5.natac.entity.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Mob extends Entity {

    protected float speed;

    protected Vector2 targetTilePos;
    protected Vector2 homePos;

    protected int travelCounter = 0;

    protected UUID owner;

    protected transient Color color;

    protected float alpha = 1f;

    protected Mob() {}

    protected Mob(float speed, int maxHealth, Vector2 tilePos, Vector2 position, Color color, UUID owner) {
        super(position, tilePos, new Rectangle(tilePos.x + position.x - 5, tilePos.y + position.y - 5, 10, 10), maxHealth);
        this.speed = speed;
        this.homePos = position.cpy();
        this.owner = owner;
        this.color = color;
    }

    public void seek(Vector2 target) {

        Vector2 displacement = target.cpy().sub(position);
        displacement.clamp(speed /5f, speed / 5f);

        Vector2 newPosition;
        if (position.dst(target) < speed) newPosition = target;
        else newPosition = position.cpy().add(displacement);

        if (Natac.instance.getGame().getMap().findTile(tilePos).collides(this, newPosition.cpy().sub(position))) return;

        PacketUtil.repositionMob(this, newPosition);
//        position = newPosition;
    }

    public void update() {

        collisionBox.setPosition(tilePos.x + position.x - (collisionBox.width /2f), tilePos.y + position.y - (collisionBox.height / 2f));

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

    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes, Vector2 center, float scale) {
        Color drawColor = new Color(color);
        drawColor.a = alpha;
        shapes.setColor(drawColor);
        shapes.filledCircle(center.cpy().add(position.cpy().scl(scale)), (collisionBox.getWidth() / 2f) * scale);

        if (Settings.debugMode) {
            shapes.setColor(Color.RED);
            shapes.setDefaultLineWidth(1f);
            shapes.rectangle(getBounds(center, scale));
        }

    }

    public UUID getOwner() { return owner; }

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

        PacketUtil.moveMob(this, closest.getTilePosition(), current.getTilePosition());

    }

    public void incrementTravel() {
        travelCounter++;
    }

    public void move(Tile from, Tile to) {
        from.remove(this);
        to.add(this);
        tilePos = to.getTilePosition();
        Vector2 displacement =
            from.getTilePosition().cpy()
                .sub(to.getTilePosition());
        position.add(displacement);
        to.defog();

        if (tilePos.epsilonEquals(targetTilePos)) targetTilePos = null;
    }

}
