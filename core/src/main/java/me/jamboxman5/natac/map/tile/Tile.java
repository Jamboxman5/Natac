package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.Player;
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

    private Tile up;
    private Tile down;
    private Tile lu;
    private Tile ld;
    private Tile ru;
    private Tile rd;

    private final List<Structure> buildings;
    private final List<Unit> occupants;

    public Tile(float x, float y, TileState state) {
        bounds = new Hexagon(x, y);
        this.state = state;
        this.type = getRandomType();

        buildings = new ArrayList<>();
        occupants = new ArrayList<>();

        if (Math.random() > 0.8) buildings.add(new Ruins(this));
    }

    private Sprite sprite;

    private final Hexagon bounds;

    private TileState state;

    private float highlightWidth = 1f;

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes) {

        if (state == TileState.HIDDEN) return;

        shapes.setColor(type.tileColor);
        shapes.filledPolygon(bounds.shape);
        shapes.setColor(state.tileColor);
        shapes.filledPolygon(bounds.shape);

        shapes.setDefaultLineWidth(highlightWidth);
        shapes.setColor(Color.WHITE);
        shapes.polygon(bounds.shape, JoinType.POINTY);

        for (Unit u : occupants) u.draw(batch, shapes);
        for (Structure s : buildings) s.draw(batch, shapes);


    }

    public void claim(Player p) {
        owner = UUID.fromString(p.getID());
        p.giveTile(this);
        setState(TileState.CLAIMED);
    }

    public void update(Vector2 touchPos) {

        bounds.update(touchPos);
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


    private static class Hexagon {
        private float currentScale = 1f;

        private final Polygon shape;


        public Hexagon(float x, float y) {
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
            shape.setPosition(x, y);
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


}


