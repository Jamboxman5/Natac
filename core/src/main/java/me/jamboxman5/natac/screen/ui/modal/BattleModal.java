package me.jamboxman5.natac.screen.ui.modal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.units.Unit;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileType;
import me.jamboxman5.natac.screen.ui.elements.scroll.StructureScroller;
import me.jamboxman5.natac.screen.ui.elements.scroll.UnitScroller;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class BattleModal extends Stage {

    private Sprite selectedTileSprite = null;
    private Polygon selectedTileHighlight = null;

    private final Tile selectedTile;

    private final float modalScale = 6f;

    int width = Settings.screenWidth / 10;
    int height = Settings.screenHeight / 10;

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    Button recruitButton;

    UnitScroller unitSelector;


    int margin = 40;

    Vector2 tileCenter = new Vector2(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);


    public BattleModal(Tile t) {

        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        this.selectedTile = t;
        selectedTileSprite = new Sprite(selectedTile.getSprite());
        selectedTileHighlight = generateHighlight();

        if (t.getOwner().equals(Natac.instance.player.getID()) && t.hasBarracks()) {
//            addRecruitButton();
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (unitSelector != null) {
            unitSelector.update();
            if (!unitSelector.hasParent()) unitSelector = null;
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 touchPos = new Vector2(screenX, Settings.screenHeight - screenY);

        for (Structure s : selectedTile.getStructures()) {
            if (s.getBounds(tileCenter, modalScale).contains(touchPos)) {
                return true;
            }
        }

        for (Unit u : selectedTile.getUnits()) {
            if (u.getBounds(tileCenter, modalScale).contains(touchPos)) {
                Natac.instance.getGame().selectUnit(u);
                return true;
            }
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    public void addRecruitButton() {
        if (recruitButton != null) return;

        float x = (Settings.screenWidth / 2f) - (width/2f);
//        recruitButton = getButton("Recruit", getRecruitAction(this), (int) width, (int) height, x + (width*2f) + (margin*2f), margin * 2.5f);

        addActor(recruitButton);
    }

    private float bgAlpha = 0f;
    private final float bgTargetAlpha = 0.5f;

    public void draw(SpriteBatch batch, ShapeDrawer shapes) {

        bgAlpha = MathUtils.lerp(bgAlpha, bgTargetAlpha, 0.02f);

        shapes.setColor(new Color(0,0,0, bgAlpha));
        shapes.filledRectangle(0, 0, Settings.screenWidth, Settings.screenHeight);

        selectedTileSprite.setCenter(tileCenter.x, tileCenter.y);
        selectedTileSprite.setScale(modalScale);
        selectedTileSprite.draw(batch);

        shapes.setDefaultLineWidth(10f);
        shapes.setColor(Color.WHITE);
        shapes.polygon(selectedTileHighlight, JoinType.POINTY);

        if (selectedTile.getType() == TileType.MOUNTAINS) {
            Sprite layer = new Sprite(Tile.mountainsLayer);
            layer.setScale(modalScale);
            layer.setCenter(tileCenter.x, tileCenter.y);
            layer.draw(batch);
        }

        for (Entity e : selectedTile.getEntities()) {
            e.draw(batch, shapes, tileCenter, modalScale);
        }

        draw();

    }

    private Polygon generateHighlight() {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            float stretchFactor = 1.5f;
            float radius = 55 * modalScale;
            vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
        }
        Polygon shape = new Polygon(vertices);
        shape.setPosition(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);
        shape.setOrigin(0,0);
        return shape;

    }

    private Button getButton(String txt, ChangeListener clickAction, float w, float h, float x, float y) {
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
