package me.jamboxman5.natac.screen.ui.modal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    Button backButton;


    DragAndDrop dragAndDrop;
    ScrollPane scrollPane;
    Table buttonOrganizer;

    Array<Button> popupButtons;

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

        createBuildMenu();

    }

    private void createBuildMenu() {

        dragAndDrop = new DragAndDrop();

        buttonOrganizer = new Table();
        buttonOrganizer.top();

        Button bb = new TextButton("Barracks", skin);
        Button bb2 = new TextButton("Butt", skin);
        Button bb3 = new TextButton("Butt", skin);
        Button bb4 = new TextButton("Butt", skin);
        Button bb5 = new TextButton("Butt", skin);
        Button bb6 = new TextButton("Butt", skin);

        buttonOrganizer.add(bb).width(290).height(200).pad(5).row();
        buttonOrganizer.add(bb2).width(290).height(200).pad(5).row();
        buttonOrganizer.add(bb3).width(290).height(200).pad(5).row();
        buttonOrganizer.add(bb4).width(290).height(200).pad(5).row();
        buttonOrganizer.add(bb5).width(290).height(200).pad(5).row();
        buttonOrganizer.add(bb6).width(290).height(200).pad(5).row();

        dragAndDrop.addSource(new DragAndDrop.Source(bb) {
            @Override
            public DragAndDrop.Payload dragStart(
                InputEvent event,
                float x,
                float y,
                int pointer) {

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(StructureSelection.BARRACKS);

                return payload;
            }

            @Override
            public void dragStop(
                InputEvent event,
                float x,
                float y,
                int pointer,
                DragAndDrop.Payload payload,
                DragAndDrop.Target target) {

                StructureSelection selected = (StructureSelection) payload.getObject();
                Vector2 dropPos = new Vector2(Gdx.input.getX(), Settings.screenHeight - Gdx.input.getY());


                if (!selectedTileHighlight.contains(dropPos)) return;

                PacketUtil.buildStructure(new Barracks(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos)), selectedTile.getTilePosition());
                PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, -selected.goldCost, -selected.resourceCost);
            }
        });

        scrollPane = new ScrollPane(buttonOrganizer);
        scrollPane.setSize(300, Settings.screenHeight - (margin * 2));
        scrollPane.setPosition(Settings.screenWidth - 300 - margin, margin);
        scrollPane.setScrollingDisabled(true, false);

    }

    private Vector2 unprojectDropPos(Vector2 dropPos) {
        return dropPos.cpy().sub(tileCenter).scl(1f/5f);
    }

    private float bgAlpha = 0f;
    private final float bgTargetAlpha = 0.5f;

    public void drawSelectedTileMenu(SpriteBatch batch, ShapeDrawer shapes) {

        bgAlpha = MathUtils.lerp(bgAlpha, bgTargetAlpha, 0.02f);

        shapes.setColor(new Color(0,0,0, bgAlpha));
        shapes.filledRectangle(0, 0, Settings.screenWidth, Settings.screenHeight);

        selectedTileSprite.setCenter(tileCenter.x, tileCenter.y);
        selectedTileSprite.setScale(5f * 0.9f, 5f * 0.9f);
        selectedTileSprite.draw(batch);

        shapes.setDefaultLineWidth(10f);
        shapes.setColor(Color.WHITE);
        shapes.polygon(selectedTileHighlight, JoinType.POINTY);

        if (selectedTile.getType() == TileType.MOUNTAINS) {
            Sprite layer = new Sprite(Tile.mountainsLayer);
            layer.setScale(5f * 0.9f);
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
            int radius = 250;
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
//                buildButton.remove();
                addActor(scrollPane);
                setScrollFocus(scrollPane);
            }
        };
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

    private enum StructureSelection {

        BARRACKS("Barracks", 50, 0);

        public final String name;
        public final int resourceCost;
        public final int goldCost;

        public String toString() {
            String s = "";
            s += name;
            if (goldCost > 0) s += "($" + goldCost + ")";
            if (resourceCost > 0) s += "(" + resourceCost + "R)";
            return s;
        }

        StructureSelection(String name, int goldCost, int resourceCost) {
            this.name = name;
            this.goldCost = goldCost;
            this.resourceCost = resourceCost;
        }
    }
}
