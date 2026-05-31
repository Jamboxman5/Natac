package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.sfx.Sounds;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.structures.constructed.TownHall;
import me.jamboxman5.natac.structures.generated.Ruins;
import me.jamboxman5.natac.units.Unit;
import me.jamboxman5.natac.units.army.Soldier;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tile {
    private UUID owner;
    private TileType type;
    private int yield;
    private int level;

    private int health;
    private int defense;

    private final List<Structure> buildings;
    private final List<Unit> occupants;

    private float currentScale = 1f;

    private transient Sprite sprite;
    private transient boolean isFogged;
    private transient boolean soundPlayed = false;

    private Vector2 pos;

    public Tile() {
        buildings = new ArrayList<>();
        occupants = new ArrayList<>();
        isFogged = true;
    }

    public Tile(float x, float y, TileState state) {
        this.state = state;
        this.type = getRandomType();

        pos = new Vector2(x, y);
        bounds = new Hexagon(pos);

        buildings = new ArrayList<>();
        occupants = new ArrayList<>();

        isFogged = true;

        if (Math.random() > 0.8 && state != TileState.STARTING) buildings.add(new Ruins(pos));
    }


    private transient Hexagon bounds;

    private TileState state;

    private float highlightWidth = 1f;

    private transient float opacity = 0;

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes, GameScreen.SelectionState tileSelectState) {

        if (bounds == null) {
            bounds = new Hexagon(pos);
        }

        if (state == TileState.HIDDEN) return;

        if (sprite == null) sprite = new Sprite(type.texture);

        if (!isFogged) {

            sprite.setCenter(bounds.shape.getX(), bounds.shape.getY());
            sprite.setOriginCenter();
            sprite.draw(batch);

            for (Unit u : occupants) u.draw(batch, shapes);
            for (Structure s : buildings) s.draw(batch, shapes);
        }

        if (tileSelectState == GameScreen.SelectionState.BASE && state == TileState.STARTING) {
            if (opacity <= 0) opacity = 0.7f;
            Color fill = new Color(state.tileColor);
            fill.a = opacity;
            shapes.setColor(fill);
            shapes.filledPolygon(bounds.shape);
            opacity -= 0.0025f;
        } else if (tileSelectState == GameScreen.SelectionState.OWNED && state == TileState.CLAIMED) {
            if (opacity <= 0) opacity = 0.7f;
            Color fill = new Color(state.tileColor);
            fill.a = opacity;
            shapes.setColor(fill);
            shapes.filledPolygon(bounds.shape);
            opacity -= 0.0025f;
        } else {
            shapes.setColor(state.tileColor);
            shapes.filledPolygon(bounds.shape);
            if (opacity > 0) opacity -= 0.0025f;
            Color fog = Color.BLACK;
            fog.a = opacity;
            shapes.setColor(fog);
            shapes.filledPolygon(bounds.shape);
        }

        if (isFogged) {
            opacity = 1f;
        }

        shapes.setDefaultLineWidth(highlightWidth);
        shapes.setColor(Color.WHITE);
        shapes.polygon(bounds.shape, JoinType.POINTY);

    }

    private void defogNeighbors(int radius) {
        if (radius == 0) {
            defog();
            return;
        }

        radius--;
        defog();
        for (Tile t : getNeighbors()) t.defogNeighbors(radius);
    }

    public List<Tile> getNeighbors() {
        return Natac.instance.getGame().getMap().getNeighbors(this);
    }


    private void defog() {
        if (state != TileState.HIDDEN) isFogged = false;
    }

    public void claim(UUID claimingPlayerID, boolean sendPacket) {
        owner = claimingPlayerID;

        if (Natac.instance.player.getID().equals(claimingPlayerID)) {
            setState(TileState.CLAIMED);
            defog();
            defogNeighbors(Natac.instance.getGame().getMap().getDefogTileRadius());
        }
        else setState(TileState.ENEMY_CLAIMED);

        if (sendPacket) {
            PacketClaimTile packet = new PacketClaimTile();
            packet.claimingID = claimingPlayerID;
            packet.tilePos = pos;
            Natac.instance.getClientManager().sendPacketTCP(packet);
        }

        if (Natac.instance.getGame().getState() == GameScreen.State.CLAIM) {

            PacketUtil.buildStructure(new TownHall(Natac.instance.player.getPlayerClass(), pos), pos);
            PacketUtil.spawnUnit(new Soldier(pos, new Vector2(-20, -20), owner), pos);
            PacketUtil.spawnUnit(new Soldier(pos, new Vector2(20, -20), owner), pos);

            Natac.instance.getGame().setState(GameScreen.State.WAIT);
        }
    }

    public void update(Vector2 touchPos) {

        if (bounds == null) {
            bounds = new Hexagon(pos);
        }

        if (state == TileState.STARTING && isFogged) defog();

        bounds.update(touchPos);

        if (sprite == null) sprite = new Sprite(type.texture);

        float targetScale = bounds.contains(touchPos) ? 1f : .9f;

        if (bounds.contains(touchPos) && state != TileState.HIDDEN) {
            if (!soundPlayed) {
                Sounds.TILE_HOVER.play();
                soundPlayed = true;
            }
        } else {
            soundPlayed = false;
        }

        currentScale = MathUtils.lerp(currentScale, targetScale, 0.1f);

        sprite.setScale(currentScale, currentScale);

        for (Unit u : occupants) u.update();
        for (Structure s : buildings) s.update();

        float targetHighlightWidth = bounds.contains(touchPos) ? 3f : 1.0f;
        highlightWidth = MathUtils.lerp(highlightWidth, targetHighlightWidth, 0.05f);

    }

    public void addUnit(Unit unit) { occupants.add(unit); }
    public void addStructure(Structure structure) { buildings.add(structure); }

    public boolean contains(Vector2 point) {
        return bounds.shape.contains(point);
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public Vector2 getTilePosition() { return new Vector2(bounds.shape.getX(), bounds.shape.getY()); }

    public TileState getState() { return state;
    }

    public UUID getOwner() { return owner; }

    public Sprite getSprite() { return sprite; }

    public List<Structure> getStructures() { return buildings; }

    public List<Unit> getUnits() { return occupants; }

    public static class Hexagon {
        private float currentScale = 1f;

        private final Polygon shape;


        public Hexagon(Vector2 center) {
            float[] vertices = new float[12];
            for (int i = 0; i < 6; i++) {
                float angle = i * MathUtils.PI / 3;
                // Multiply the X-offset by the stretch factor
                float stretchFactor = 1.5f;
                int radius = 50;
                vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
                vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
            }
            shape = new Polygon(vertices);
            shape.setPosition(center.x, center.y);
            shape.setOrigin(0,0);
        }

        public void update(Vector2 touchPos) {
            float targetScale = shape.contains(touchPos.x, touchPos.y) ? 1.1f : 1.0f;

            currentScale = MathUtils.lerp(currentScale, targetScale, 0.1f);

            shape.setScale(currentScale, currentScale);
        }

        public float[] getVertices() { return shape.getTransformedVertices(); }
        public boolean contains(Vector2 point) { return shape.contains(point); }
    }

    private static TileType getRandomType() {
        TileType[] types = TileType.values();
        int idx = (int) (Math.random() * types.length);
        return types[idx];
    }

    public boolean isAt(Vector2 pos) {
        return this.pos.epsilonEquals(pos);
    }

    public float getCurrentScale() { return currentScale; }


}


