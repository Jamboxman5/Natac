package me.jamboxman5.natac.screen.ui.elements.scroll;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.utils.Align;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.units.Unit;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.screen.ui.elements.select.UnitSelection;
import me.jamboxman5.natac.screen.ui.modal.SelectedTileModal;
import me.jamboxman5.natac.entity.units.army.Soldier;
import me.jamboxman5.natac.sfx.Sounds;
import me.jamboxman5.natac.util.Settings;

public class UnitScroller extends DDScroller {

    public UnitScroller(SelectedTileModal parent, Tile selectedTile, Polygon selectedTileBounds, Vector2 tileCenter, Rectangle bounds) {
        super(selectedTile, selectedTileBounds, tileCenter, bounds, true, Align.left, Align.left);

        for (UnitSelection selection : UnitSelection.values()) {
            Button b = new TextButton(selection.toString(), skin);
            addButton(b, 290, 200, 5);

            dragAndDrop.addSource(new DragAndDrop.Source(b) {
                @Override
                public DragAndDrop.Payload dragStart(
                    InputEvent event,
                    float x,
                    float y,
                    int pointer) {

                    if (Natac.instance.player.getGold() < selection.goldCost) return null;
                    if (Natac.instance.player.getResources() < selection.resourceCost) return null;

                    DragAndDrop.Payload payload = new DragAndDrop.Payload();
                    payload.setObject(selection);

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

                    UnitSelection selected = (UnitSelection) payload.getObject();
                    Vector2 dropPos = new Vector2(Gdx.input.getX(), Settings.screenHeight - Gdx.input.getY());

                    if (!selectedTileBounds.contains(dropPos)) return;

                    Unit spawning = null;

                    switch(selected) {
                        case SOLDIER:
                            spawning = new Soldier(selectedTile.getTilePosition(), unprojectDropPos(dropPos), selectedTile.getOwner());
                            break;
                    }

                    if (selectedTile.collides(spawning)) spawning = null;

                    if (spawning == null) return;

                    PacketUtil.spawnUnit(spawning, selectedTile.getTilePosition());

                    PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, -selected.goldCost, -selected.resourceCost);
                    Sounds.UNIT_SPAWN.play();
                }
            });

        }



    }
}
