package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

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

    public void draw(SpriteBatch batch, ShapeRenderer shapes) {
        shapes.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        shapes.setColor(Color.WHITE);

        float radius = 50;
        float columns = 5;
        float rows = 5;


        float startX = 200;
        float startY = 200;

        float gap = 10.0f;
        float stretchFactor = 1.5f;

        float verticalStep = (float) ((Math.sqrt(3) * radius) + gap);

        float horizontalStep = 1.5f * (radius * stretchFactor) + (gap * 0.866f * stretchFactor);

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                float x = startX + (col * horizontalStep);
                float y = startY + (row * verticalStep);

                if (col % 2 != 0) y += verticalStep / 2f;

                drawStretchedHexagon(shapes, x, y, radius, stretchFactor);
            }
        }

        shapes.end();
    }

    public void drawStretchedHexagon(ShapeRenderer sr, float centerX, float centerY, float radius, float stretchFactor) {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            vertices[i * 2] = centerX + (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = centerY + (radius * MathUtils.sin(angle));
        }
        sr.polygon(vertices);
    }

    public enum TileType {
        PLAINS, FOREST, RADIATION;
    }
}


