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

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    protected Selector(SelectedTileModal parent, Tile selectedTile, Polygon selectedTileBounds, Vector2 tileCenter, Rectangle bounds) {
        super(new Table());

        this.parent = parent;

        this.selectedTile = selectedTile;
        this.selectedTileBounds = selectedTileBounds;
        this.tileCenter = tileCenter;

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
        buttonOrganizer.add(back).width(290).height(100).pad(5).row();

        setSize(bounds.width, bounds.height);
        setPosition(bounds.x, bounds.y);
        setScrollingDisabled(true, false);

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
}
