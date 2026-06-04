package me.jamboxman5.natac.screen.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.structures.constructed.Barracks;
import me.jamboxman5.natac.util.Settings;

public class StructureSelector extends ScrollPane {

    DragAndDrop dragAndDrop;
    Table buttonOrganizer;

    Tile selectedTile;
    Polygon selectedTileBounds;
    Vector2 tileCenter;

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    public StructureSelector(Tile selectedTile, Polygon bounds, Vector2 tileCenter, float margin) {
        super(new Table());

        this.selectedTile = selectedTile;
        this.selectedTileBounds = bounds;
        this.tileCenter = tileCenter;

        buttonOrganizer = (Table) getActor();
        dragAndDrop = new DragAndDrop();

        buttonOrganizer.top();
        Button bb = new TextButton(Selection.BARRACKS.toString(), skin);
        buttonOrganizer.add(bb).width(290).height(200).pad(5).row();

        dragAndDrop.addSource(new DragAndDrop.Source(bb) {
            @Override
            public DragAndDrop.Payload dragStart(
                InputEvent event,
                float x,
                float y,
                int pointer) {

                if (Natac.instance.player.getGold() < Selection.BARRACKS.goldCost) return null;
                if (Natac.instance.player.getResources() < Selection.BARRACKS.resourceCost) return null;

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(Selection.BARRACKS);

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

                Selection selected = (Selection) payload.getObject();
                Vector2 dropPos = new Vector2(Gdx.input.getX(), Settings.screenHeight - Gdx.input.getY());


                if (!selectedTileBounds.contains(dropPos)) return;

                PacketUtil.buildStructure(new Barracks(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos)), selectedTile.getTilePosition());
                PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, -selected.goldCost, -selected.resourceCost);
            }
        });

        setSize(300, Settings.screenHeight - (margin * 2));
        setPosition(Settings.screenWidth - 300 - margin, margin);
        setScrollingDisabled(true, false);

    }

    private Vector2 unprojectDropPos(Vector2 dropPos) {
        return dropPos.cpy().sub(tileCenter).scl(1f/5f);
    }


    private enum Selection {

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
