package me.jamboxman5.natac.screen.ui.modal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileType;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.screen.ui.elements.StructureSelector;
import me.jamboxman5.natac.screen.ui.elements.UnitSelector;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.structures.constructed.Barracks;
import me.jamboxman5.natac.structures.constructed.TownHall;
import me.jamboxman5.natac.units.Unit;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class SelectedTileModal extends Stage {

    private Sprite selectedTileSprite = null;
    private Polygon selectedTileHighlight = null;

    private final Tile selectedTile;

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    Button buildButton;
    Button recruitButton;
    Button backButton;

    StructureSelector structureSelector;
    UnitSelector unitSelector;


    int margin = 40;

    Vector2 tileCenter = new Vector2(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);


    public SelectedTileModal(Tile t) {

        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        this.selectedTile = t;
        selectedTileSprite = new Sprite(selectedTile.getSprite());
        selectedTileHighlight = generateHighlight();

        int width = Settings.screenWidth / 10;
        int height = Settings.screenHeight / 10;

        float x = (Settings.screenWidth / 2f) - (margin / 2f) - width;

        backButton = getButton("Back", getBackAction(), width, height, x, margin * 2.5f);
        buildButton = getButton("Build", getBuildAction(), width, height, x + width + margin, margin * 2.5f);

        addActor(buildButton);
        addActor(backButton);

        if (t.hasBarracks()) addRecruitButton();

        Rectangle structureSelectorBounds = new Rectangle(Settings.screenWidth - 300 - margin, margin, 300, Settings.screenHeight - (margin * 2));
        structureSelector = new StructureSelector(this, selectedTile, selectedTileHighlight, tileCenter, structureSelectorBounds);

        Rectangle unitSelectorBounds = new Rectangle(margin, margin, 300, Settings.screenHeight - (margin * 2));
        unitSelector = new UnitSelector(this, selectedTile, selectedTileHighlight, tileCenter, unitSelectorBounds);


    }

    public void addRecruitButton() {
        if (recruitButton != null) return;

        float x = (Settings.screenWidth / 2f) - margin - (backButton.getWidth() * 1.5f);
        float width = backButton.getWidth();
        float height = backButton.getHeight();

        backButton.setPosition(x, backButton.getY());
        buildButton.setPosition(x + width + margin, buildButton.getY());

        recruitButton = getButton("Recruit", getRecruitAction(), (int) width, (int) height, x + (width*2f) + (margin*2f), margin * 2.5f);
        addActor(recruitButton);
    }

    private float bgAlpha = 0f;
    private final float bgTargetAlpha = 0.5f;

    public void drawSelectedTileMenu(SpriteBatch batch, ShapeDrawer shapes) {

        bgAlpha = MathUtils.lerp(bgAlpha, bgTargetAlpha, 0.02f);

        shapes.setColor(new Color(0,0,0, bgAlpha));
        shapes.filledRectangle(0, 0, Settings.screenWidth, Settings.screenHeight);

        selectedTileSprite.setCenter(tileCenter.x, tileCenter.y);
        selectedTileSprite.setScale(5f, 5f);
        selectedTileSprite.draw(batch);

        shapes.setDefaultLineWidth(10f);
        shapes.setColor(Color.WHITE);
        shapes.polygon(selectedTileHighlight, JoinType.POINTY);

        if (selectedTile.getType() == TileType.MOUNTAINS) {
            Sprite layer = new Sprite(Tile.mountainsLayer);
            layer.setScale(5f);
            layer.setCenter(tileCenter.x, tileCenter.y);
            layer.draw(batch);
        }

        for (Structure structure : selectedTile.getStructures()) {
            structure.drawModal(batch, shapes, tileCenter);
        }

        for (Unit unit : selectedTile.getUnits()) {
            unit.drawModal(batch, shapes, tileCenter);
        }

        draw();

    }

    private Polygon generateHighlight() {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            float stretchFactor = 1.5f;
            int radius = 56 * 5;
            vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
        }
        Polygon shape = new Polygon(vertices);
        shape.setPosition(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);
        shape.setOrigin(0,0);
        return shape;

    }

    private Button getButton(String txt, ChangeListener clickAction, int w, int h, float x, float y) {
        TextButton button = new TextButton(txt, skin);
        button.setPosition(x, y);
        button.setSize(w, h);

        button.addListener(clickAction);

        button.getStyle().font.getData().setScale(2f);
        button.getStyle().disabledFontColor = Color.GRAY;

        return button;
    }

    public ChangeListener getBackAction() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.getGame().getMap().deselectTile();
            }
        };
    }

    public ChangeListener getBuildAction() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeSelector();
                addActor(structureSelector);
                setScrollFocus(structureSelector);
            }
        };
    }

    public ChangeListener getRecruitAction() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closeSelector();
                addActor(unitSelector);
                setScrollFocus(unitSelector);
            }
        };
    }

    public void closeSelector() {
        structureSelector.remove();
        unitSelector.remove();
    }

//    public ChangeListener getBuildConfirmAction() {
//        return new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//
//                StructureSelection building = buildSelector.getSelected();
//
//                int cost = 0;
//                Structure toBuild = null;
//
//                switch(building) {
//                    case NONE:
//                        buildSelector.remove();
//                        buildConfirmButton.remove();
//                        addActor(buildButton);
//                        break;
//                    case BARRACKS:
//                        cost = Barracks.goldCost;
//                        toBuild = new Barracks(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), new Vector2());
//                        break;
//                }
//
//                if (toBuild == null || Natac.instance.player.getGold() < cost) return;
//
//                buildSelector.remove();
//                buildConfirmButton.remove();
//                addActor(buildButton);
//
//                PacketUtil.buildStructure(toBuild, selectedTile.getTilePosition());
//                PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, -cost, 0);
//            }
//        };
//    }


}
