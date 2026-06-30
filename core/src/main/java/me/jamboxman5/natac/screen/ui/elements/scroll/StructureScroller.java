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
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.structures.constructed.*;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.screen.ui.elements.select.StructureSelection;
import me.jamboxman5.natac.screen.ui.modal.SelectedTileModal;
import me.jamboxman5.natac.sfx.Sounds;
import me.jamboxman5.natac.util.Settings;

public class StructureScroller extends DDScroller {

    SelectedTileModal parent;

    public StructureScroller(SelectedTileModal parent, Tile selectedTile, Polygon selectedTileBounds, Vector2 tileCenter, Rectangle bounds) {
        super(selectedTile, selectedTileBounds, tileCenter, bounds, true, Align.right, Align.right);

        this.parent = parent;

        for (StructureSelection selection : StructureSelection.values()) {
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

                    StructureSelection selected = (StructureSelection) payload.getObject();
                    Vector2 dropPos = new Vector2(Gdx.input.getX(), Settings.screenHeight - Gdx.input.getY());


                    if (!selectedTileBounds.contains(dropPos)) return;


                    Structure placing = null;

                    switch(selected) {
                        case BARRACKS:
                            placing = new Barracks(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                        case DEPOT:
                            placing = new Depot(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                        case LOGGER:
                            placing = new Logger(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                        case QUARRY:
                            placing = new Quarry(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                        case LIBRARY:
                            placing = new Library(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                        case SCOUT_TOWER:
                            placing = new ScoutTower(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                        case ARMY_OUTPOST:
                            placing = new ArmyOutpost(Natac.instance.player.getPlayerClass(), selectedTile.getTilePosition(), unprojectDropPos(dropPos));
                            break;
                    }

                    if (selectedTile.collides(placing)) placing = null;

                    if (placing == null) return;

                    PacketUtil.buildStructure(placing, selectedTile.getTilePosition());
                    Sounds.STRUCTURE_DROP.play();

                    PacketUtil.createStatChange(Natac.instance.player, 0, 0, 0, 0, -selected.goldCost, -selected.resourceCost);
                    parent.addRecruitButton();
                }
            });
        }





    }

}
