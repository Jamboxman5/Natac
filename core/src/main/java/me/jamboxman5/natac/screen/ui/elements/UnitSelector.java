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
import me.jamboxman5.natac.structures.constructed.Barracks;
import me.jamboxman5.natac.units.army.Soldier;
import me.jamboxman5.natac.util.Settings;

public class UnitSelector extends Selector {

    public UnitSelector(SelectedTileModal parent, Tile selectedTile, Polygon selectedTileBounds, Vector2 tileCenter, Rectangle bounds) {
        super(parent, selectedTile, selectedTileBounds, tileCenter, bounds, true);

        Button bb = new TextButton(Selection.SOLDIER.toString(), skin);
        addButton(bb, 290, 200, 5);

        dragAndDrop.addSource(new DragAndDrop.Source(bb) {
            @Override
            public DragAndDrop.Payload dragStart(
                InputEvent event,
                float x,
                float y,
                int pointer) {

                if (Natac.instance.player.getGold() < Selection.SOLDIER.goldCost) return null;
                if (Natac.instance.player.getResources() < Selection.SOLDIER.resourceCost) return null;

                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(Selection.SOLDIER);

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

                PacketUtil.spawnUnit(new Soldier(selectedTile.getTilePosition(), unprojectDropPos(dropPos), selectedTile.getOwner()), selectedTile.getTilePosition());
                PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, -selected.goldCost, -selected.resourceCost);
            }
        });

    }
}
