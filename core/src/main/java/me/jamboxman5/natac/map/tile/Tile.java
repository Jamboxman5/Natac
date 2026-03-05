package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import me.jamboxman5.natac.Natac;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.List;
import java.util.UUID;

public class Tile {
    private int passability;
    private UUID owner;
    private TileType type;
    private int yield;
    private int level;

    private int health;
    private int defense;

    private List contents;

    private Sprite sprite;

    private final Hexagon bounds;

    private TileState state;

    public Tile(float x, float y, TileState state) {
        bounds = new Hexagon(x, y);
        this.state = state;
    }

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes) {
        switch (state) {
            case HIDDEN:
                return;
            case BLOCKED:
                shapes.setColor(new Color(0, 0, .2f, .5f));
                shapes.filledPolygon(bounds.getVertices());
                shapes.setColor(Color.WHITE);
                shapes.polygon(bounds.getVertices());
                return;
            case SELECTABLE:

                shapes.setColor(new Color(.4f, .4f, .4f, .5f));
                shapes.filledPolygon(bounds.getVertices());
                shapes.setColor(Color.WHITE);
                shapes.polygon(bounds.getVertices());
                return;
            case SELECTED:
                return;
        }


    }

    public void update(Vector2 touchPos) {

        bounds.update(touchPos);

    }

    public enum TileType {
        PLAINS, FOREST, RADIATION;
    }

    public enum TileState {
        HIDDEN, BLOCKED, SELECTABLE, SELECTED
    }


    private static class Hexagon {
        private float currentScale = 1f;

        private final Polygon bounds;


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
            bounds = new Polygon(vertices);
            bounds.setPosition(x, y);
            bounds.setOrigin(0,0);
        }

        public void update(Vector2 touchPos) {
            float targetScale = bounds.contains(touchPos.x, touchPos.y) ? 1.1f : 1.0f;

            currentScale = MathUtils.lerp(currentScale, targetScale, 0.1f);

            bounds.setScale(currentScale, currentScale);
        }

        public float[] getVertices() { return bounds.getTransformedVertices(); }
    }
}


