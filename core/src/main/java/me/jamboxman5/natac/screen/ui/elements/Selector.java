package me.jamboxman5.natac.screen.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.screen.ui.modal.SelectedTileModal;
import me.jamboxman5.natac.units.army.Soldier;
import me.jamboxman5.natac.util.Settings;

public class Selector extends ScrollPane {

    protected DragAndDrop dragAndDrop;
    protected Table buttonOrganizer;

    protected Tile selectedTile;
    protected Polygon selectedTileBounds;
    protected Vector2 tileCenter;

    protected SelectedTileModal parent;
    protected boolean isVertical;

    protected Vector2 targetPos;

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    protected Selector(SelectedTileModal parent, Tile selectedTile, Polygon selectedTileBounds, Vector2 tileCenter, Rectangle bounds, boolean isVertical) {
        super(new Table());

        this.parent = parent;

        this.selectedTile = selectedTile;
        this.selectedTileBounds = selectedTileBounds;
        this.tileCenter = tileCenter;

        this.isVertical = isVertical;

        buttonOrganizer = (Table) getActor();
        dragAndDrop = new DragAndDrop();

        buttonOrganizer.top();

        Button back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.closeSelector();
            }
        });
        addButton(back, 290, 100, 5);

        setSize(bounds.width, bounds.height);
        setPosition(bounds.x, bounds.y);
        setScrollingDisabled(isVertical, !isVertical);

        targetPos = new Vector2(bounds.x, bounds.y);

    }

    public void animateEntrance(int alignFrom) {
        if (alignFrom == Align.left) {
            moveBy(-500, 0);
        } else if (alignFrom == Align.right) {
            moveBy(500, 0);
        }
    }

    public void update() {
        if (targetPos.epsilonEquals(getX(), getY())) return;
        float xDiff = targetPos.x - getX();
        if (xDiff > 0) moveBy(20, 0);
        if (xDiff < 0) moveBy(-20, 0);

        float yDiff = targetPos.y - getY();
        if (yDiff > 0) moveBy(0, 20);
        if (yDiff < 0) moveBy(0, -20);
    }

    protected Vector2 unprojectDropPos(Vector2 dropPos) {
        return dropPos.cpy().sub(tileCenter).scl(1f/5f);
    }


    protected enum Selection {

        SOLDIER("Soldier", 50, 0),
        BARRACKS("Barracks", 50, 0);

        public final String name;
        public final int resourceCost;
        public final int goldCost;

        public String toString() {
            String s = "";
            s += name;
            if (goldCost > 0) s += " ($" + goldCost + ")";
            if (resourceCost > 0) s += " (" + resourceCost + "R)";
            return s;
        }

        Selection(String name, int goldCost, int resourceCost) {
            this.name = name;
            this.goldCost = goldCost;
            this.resourceCost = resourceCost;
        }
    }

    protected void addButton(Button b, float w, float h, float pad) {
        buttonOrganizer.add(b).width(w).height(h).pad(pad);
        if (isVertical) buttonOrganizer.row();
    }
}
