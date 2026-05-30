package me.jamboxman5.natac.screen.ui.modal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SelectedTileModal {

    private Sprite selectedTileSprite = null;
    private Polygon selectedTileHighlight = null;

    private final Tile selectedTile;

    public SelectedTileModal(Tile t) {
        this.selectedTile = t;
        selectedTileSprite = new Sprite(selectedTile.getSprite());
        selectedTileHighlight = generateHighlight();
    }

    public void drawSelectedTileMenu(SpriteBatch batch, ShapeDrawer shapes) {

        selectedTileSprite.setCenter(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);
        selectedTileSprite.setScale(5f * 0.9f, 5f * 0.9f);
        selectedTileSprite.draw(batch);

        shapes.setDefaultLineWidth(10f);
        shapes.setColor(Color.WHITE);
        shapes.polygon(selectedTileHighlight, JoinType.POINTY);

        for (Structure structure : selectedTile.getStructures()) {
            structure.drawModal(batch, shapes, new Vector2(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50));
        }

    }

    private Polygon generateHighlight() {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            float stretchFactor = 1.5f;
            int radius = 250;
            vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
        }
        Polygon shape = new Polygon(vertices);
        shape.setPosition(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);
        shape.setOrigin(0,0);
        return shape;
    }
}
