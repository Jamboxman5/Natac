package me.jamboxman5.natac.screen.ui.elements.info;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import me.jamboxman5.natac.sfx.Sounds;
import me.jamboxman5.natac.util.Settings;

public class InfoPanel extends WidgetGroup{

    protected Vector2 targetPos;
    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));
    protected boolean markedRemove = false;

    public InfoPanel(float margin) {

        targetPos = new Vector2(margin, margin);
        float w = (Settings.screenWidth / 3f) - margin;
        float h = Settings.screenHeight - (margin * 2);

        setPosition(margin - (w * 2), margin);
        setSize(w, h);

    }

    public void update() {
        if (targetPos.epsilonEquals(getX(), getY())) {
            if (markedRemove) {
                remove();
            }
            return;
        }
        float xDiff = targetPos.x - getX();
        if (xDiff > 0) moveBy(Math.min(xDiff, 20), 0);
        if (xDiff < 0) moveBy(Math.max(xDiff, -20), 0);

        float yDiff = targetPos.y - getY();
        if (yDiff > 0) moveBy(0, Math.min(yDiff, 20));
        if (yDiff < 0) moveBy(0, Math.max(yDiff, -20));

    }

    public void animateEntrance() {

        moveBy(-getWidth() + 100, 0);

        Sounds.MENU_SLIDE_IN.play();
    }

    public void animateExit() {

        moveBy(-20, 0);
        targetPos.add(-500, 0);

        Sounds.MENU_SLIDE_OUT.play();
        markedRemove = true;
    }

}
