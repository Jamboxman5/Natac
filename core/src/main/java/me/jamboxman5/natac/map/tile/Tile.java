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
        this.type = TileType.PLAINS;
    }

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes) {

        if (state == TileState.HIDDEN) return;

        shapes.setColor(type.tileColor);
        shapes.filledPolygon(bounds.getVertices());
        shapes.setColor(state.tileColor);
        shapes.filledPolygon(bounds.getVertices());
        shapes.setColor(Color.WHITE);
        shapes.polygon(bounds.getVertices());


    }

    public void update(Vector2 touchPos) {

        bounds.update(touchPos);

    }

    public boolean contains(Vector2 point) {
        return bounds.bounds.contains(point);
    }

    public void setState(TileState state) {
        this.state = state;
    }

    public TileState getState() { return state;
    }

    public enum TileType {
        PLAINS(new Color(.2f, 0.8f, .2f, 1f)),
        FOREST(new Color(.2f, 1f, .2f, 1f)),
        RADIATION(new Color(.4f, .2f, .2f, 1f));

        public final Color tileColor;

        TileType(Color tileColor) {
            this.tileColor = tileColor;
        }
    }

    public enum TileState {
        HIDDEN(Color.BLACK),
        BLOCKED(new Color(0, 0, .2f, .5f)),
        SELECTABLE(new Color(.4f, .4f, .4f, .5f)),
        SELECTED(new Color(0, 0, .4f, .7f));

        public final Color tileColor;

        TileState(Color tileColor) {
            this.tileColor = tileColor;
        }
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


