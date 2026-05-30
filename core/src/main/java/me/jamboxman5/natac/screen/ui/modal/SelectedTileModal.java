package me.jamboxman5.natac.screen.ui.modal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SelectedTileModal extends Stage {

    private Sprite selectedTileSprite = null;
    private Polygon selectedTileHighlight = null;

    private final Tile selectedTile;

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    TextButton buildButton = new TextButton("Build", skin);
    TextButton backButton = new TextButton("Back", skin);

    public SelectedTileModal(Tile t) {

        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        this.selectedTile = t;
        selectedTileSprite = new Sprite(selectedTile.getSprite());
        selectedTileHighlight = generateHighlight();


        buildButton.getStyle().font.getData().setScale(2f);
        buildButton.getStyle().disabledFontColor = Color.GRAY;

        int width = Settings.screenWidth / 10;
        int height = Settings.screenHeight / 10;
        int margin = 40;

        float x = (Settings.screenWidth / 2f) - (margin / 2f) - width;

        backButton.setPosition(x, margin * 2.5f);
        buildButton.setPosition(x + width + margin, margin * 2.5f);

        buildButton.setSize(width, height);
        backButton.setSize(width, height);

        addActor(buildButton);
        addActor(backButton);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.getGame().getMap().deselectTile();
            }
        });

        buildButton.setDisabled(false);
        backButton.setDisabled(false);

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

        draw();

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
