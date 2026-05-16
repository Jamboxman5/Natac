package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.structures.generated.Ruins;
import me.jamboxman5.natac.units.Unit;
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

    private Vector2 pos;

    public Tile() {
        buildings = new ArrayList<>();
        occupants = new ArrayList<>();
    }

    public Tile(float x, float y, TileState state) {
        this.state = state;
        this.type = getRandomType();
        bounds = new Hexagon(pos);

        buildings = new ArrayList<>();
        occupants = new ArrayList<>();

        pos.x = x;
        pos.y = y;

        if (Math.random() > 0.8) buildings.add(new Ruins(this));
    }


    private transient Hexagon bounds;

    private TileState state;

    private float highlightWidth = 1f;

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes) {

        if (bounds == null) {
            bounds = new Hexagon(pos);
        }

        if (state == TileState.HIDDEN) return;

        if (sprite == null) sprite = new Sprite(type.texture);


        sprite.setCenter(bounds.shape.getX(), bounds.shape.getY());
        sprite.setOriginCenter();
        sprite.draw(batch);
        shapes.setColor(state.tileColor);
        shapes.filledPolygon(bounds.shape);

        shapes.setDefaultLineWidth(highlightWidth);
        shapes.setColor(Color.WHITE);
        shapes.polygon(bounds.shape, JoinType.POINTY);

        for (Unit u : occupants) u.draw(batch, shapes);
        for (Structure s : buildings) s.draw(batch, shapes);


    }

    public void claim(UUID claimingPlayerID, boolean sendPacket) {
        owner = claimingPlayerID;
        setState(TileState.CLAIMED);

        if (sendPacket) {
            PacketClaimTile packet = new PacketClaimTile();
            packet.claimingID = claimingPlayerID.toString();
            packet.tilePos = pos;
            Natac.instance.getClientManager().sendPacketTCP(packet);
        }

        if (GameScreen.getState() == GameScreen.State.CLAIM) GameScreen.setState(GameScreen.State.WAIT);
    }

    public void update(Vector2 touchPos) {

        if (bounds == null) {
            bounds = new Hexagon(pos);
        }

        bounds.update(touchPos);

        if (sprite == null) sprite = new Sprite(type.texture);

        float targetScale = bounds.contains(touchPos) ? 1f : .9f;

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


}


