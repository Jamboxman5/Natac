package me.jamboxman5.natac.screen.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.screen.ui.elements.scroll.CardScroller;
import me.jamboxman5.natac.screen.ui.elements.scroll.Scroller;
import me.jamboxman5.natac.util.Settings;

public class PlayInputStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    TextButton shopButton = new TextButton("Shop", skin);
    TextButton cardButton = new TextButton("Cards", skin);
    TextButton endButton = new TextButton("End Turn", skin);

    Scroller popupScroller;

    public PlayInputStage() {

        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        shopButton.getStyle().font.getData().setScale(2f);
        shopButton.getStyle().disabledFontColor = Color.GRAY;

        int width = Settings.screenWidth / 10;
        int height = Settings.screenHeight / 10;
        int margin = 20;

        int x = Settings.screenWidth - (margin * 2) - width;
        int y = Settings.screenHeight - (margin * 2) - height;

        shopButton.setPosition(x, y);
        cardButton.setPosition(x, y - (height) - (margin));
        endButton.setPosition(x, y - (height * 2) - (margin * 2));

        shopButton.setSize(width, height);
        cardButton.setSize(width, height);
        endButton.setSize(width, height);

        addActor(shopButton);
        addActor(cardButton);
        addActor(endButton);

        disableInput();

        endButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.endTurn();
            }
        });

        cardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (popupScroller != null) return;
                popupScroller = new CardScroller();
                popupScroller.animateEntrance();
                addActor(popupScroller);
            }
        });

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (popupScroller != null) {
            popupScroller.update();
            if (popupScroller.canRemove()) popupScroller = null;
            setScrollFocus(null);
        }
    }

    public void disableInput() {
        shopButton.setDisabled(true);
        cardButton.setDisabled(true);
        endButton.setDisabled(true);
    }

    public void enableInput() {
        shopButton.setDisabled(false);
        cardButton.setDisabled(false);
        endButton.setDisabled(false);
    }

    public boolean isDisabled() { return shopButton.isDisabled(); }



}
