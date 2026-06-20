package me.jamboxman5.natac.screen.ui.elements.scroll;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import me.jamboxman5.natac.entity.structures.constructed.Barracks;
import me.jamboxman5.natac.entity.units.army.Soldier;
import me.jamboxman5.natac.map.tile.Tile;

public class DDScroller extends Scroller {

    protected DragAndDrop dragAndDrop;

    protected Tile selectedTile;
    protected Polygon selectedTileBounds;
    protected Vector2 tileCenter;

    protected DDScroller(Tile selectedTile,
                         Polygon selectedTileBounds,
                         Vector2 tileCenter,
                         Rectangle bounds,
                         boolean isVertical,
                         int alignFrom,
                         int alignTo) {

        super(alignFrom, alignTo, isVertical, bounds);

        this.selectedTile = selectedTile;
        this.selectedTileBounds = selectedTileBounds;
        this.tileCenter = tileCenter;

        dragAndDrop = new DragAndDrop();


        setSize(bounds.width, bounds.height);
        setPosition(bounds.x, bounds.y);
        setScrollingDisabled(isVertical, !isVertical);

        targetPos = new Vector2(bounds.x, bounds.y);

    }

    protected Vector2 unprojectDropPos(Vector2 dropPos) {
        return dropPos.cpy().sub(tileCenter).scl(1f/5f);
    }

    protected void addButton(Button b, float w, float h, float pad) {
        buttonOrganizer.add(b).width(w).height(h).pad(pad);
        if (isVertical) buttonOrganizer.row();
    }

}
