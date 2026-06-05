package me.jamboxman5.natac.screen.ui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.screen.ui.modal.SelectedTileModal;
import me.jamboxman5.natac.sfx.Sounds;

public class Selector extends ScrollPane {

    protected Table buttonOrganizer;

    protected boolean isVertical;

    protected Vector2 targetPos;

    protected boolean markedRemove = false;

    protected int alignFrom;
    protected int alignTo;

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    protected Selector(int alignFrom, int alignTo, boolean isVertical, Rectangle bounds) {
        super(new Table());

        this.isVertical = isVertical;

        buttonOrganizer = (Table) getActor();
        buttonOrganizer.top();

        setSize(bounds.width, bounds.height);
        setPosition(bounds.x, bounds.y);
        setScrollingDisabled(isVertical, !isVertical);

        Button back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                animateExit();
            }
        });

        if (isVertical) {
            addButton(back, getWidth(), getWidth()/2f, 5);
        } else {
            addButton(back, getHeight() /2f, getHeight(), 5);
        }

        targetPos = new Vector2(bounds.x, bounds.y);
        this.alignFrom = alignFrom;
        this.alignTo = alignTo;

    }

    public void animateEntrance() {
        if (alignFrom == Align.left) {
            moveBy(-500, 0);
        } else if (alignFrom == Align.right) {
            moveBy(500, 0);
        }
        Sounds.MENU_SLIDE_IN.play();
    }

    public void animateExit() {
        if (alignTo == Align.left) {
            moveBy(-20, 0);
            targetPos.add(-500, 0);
        } else if (alignTo == Align.right) {
            moveBy(20, 0);
            targetPos.add(500, 0);
        } else if (alignTo == Align.top) {
            moveBy(0, 20);
            targetPos.add(0, 500);
        } else if (alignTo == Align.bottom) {
            moveBy(0, -20);
            targetPos.add(0, -500);
        }

        Sounds.MENU_SLIDE_OUT.play();
        markedRemove = true;
    }

    public void update() {
        if (targetPos.epsilonEquals(getX(), getY())) {
            if (markedRemove) {
                remove();
            }
            return;
        }
        float xDiff = targetPos.x - getX();
        if (xDiff > 0) moveBy(20, 0);
        if (xDiff < 0) moveBy(-20, 0);

        float yDiff = targetPos.y - getY();
        if (yDiff > 0) moveBy(0, 20);
        if (yDiff < 0) moveBy(0, -20);
    }

    protected void addButton(Button b, float w, float h, float pad) {
        buttonOrganizer.add(b).width(w).height(h).pad(pad);
        if (isVertical) buttonOrganizer.row();
    }

    public boolean canRemove() {
        return markedRemove && targetPos.epsilonEquals(getX(), getY());
    }
}
