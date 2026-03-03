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

    private Polygon bounds;
    private int radius = 50;
    private float stretchFactor = 1.5f;
    private Color highlight = Color.WHITE;

    public Tile(float x, float y) {
        generateHexagon(x, y);
    }

    private void generateHexagon(float x, float y) {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            vertices[i * 2] = x + (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = y + (radius * MathUtils.sin(angle));
        }
        bounds = new Polygon(vertices);
    }

    public void draw(Camera camera, SpriteBatch batch, ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        shapes.setColor(highlight);
        shapes.polygon(bounds.getTransformedVertices());
        shapes.end();
    }

    public void update(Vector2 touchPos) {
        if (bounds.contains(touchPos.x, touchPos.y)) {
            highlight = Color.RED;
        } else {
            highlight = Color.WHITE;
        }
    }

    public enum TileType {
        PLAINS, FOREST, RADIATION;
    }
}


