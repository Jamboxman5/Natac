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
    private float targetScale = 1f;
    private float currentScale = 1f;

    private Polygon bounds;
    private int radius = 50;
    private float stretchFactor = 1.5f;
    private Color highlight = Color.WHITE;
    private Color fill = new Color(0, 0, .2f, .5f);

    public Tile(float x, float y) {
        generateHexagon(x, y);
    }

    private void generateHexagon(float x, float y) {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
        }
        bounds = new Polygon(vertices);
        bounds.setPosition(x, y);
        bounds.setOrigin(0,0);
    }

    public void draw(Camera camera, SpriteBatch batch, ShapeDrawer shapes) {
        shapes.setColor(fill);
        shapes.filledPolygon(bounds.getTransformedVertices());
        shapes.setColor(highlight);
        shapes.polygon(bounds.getTransformedVertices());
    }

    public void update(Vector2 touchPos) {

        targetScale = bounds.contains(touchPos.x, touchPos.y) ? 1.1f : 1.0f;

        currentScale = MathUtils.lerp(currentScale, targetScale, 0.1f);

        bounds.setScale(currentScale, currentScale);

    }

    public enum TileType {
        PLAINS, FOREST, RADIATION;
    }
}


