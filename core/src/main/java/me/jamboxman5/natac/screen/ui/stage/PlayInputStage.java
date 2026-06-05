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
import me.jamboxman5.natac.cards.Card;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.ui.elements.CardSelector;
import me.jamboxman5.natac.screen.ui.elements.Selector;
import me.jamboxman5.natac.util.Settings;

public class PlayInputStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    TextButton claimTileButton = new TextButton("Claim", skin);
    TextButton shopButton = new TextButton("Shop", skin);
    TextButton attackButton = new TextButton("Attack", skin);
    TextButton scoutButton = new TextButton("Scout", skin);
    TextButton cardButton = new TextButton("Cards", skin);
    TextButton endButton = new TextButton("End Turn", skin);

    Selector popupSelector;

    public PlayInputStage() {

        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        claimTileButton.getStyle().font.getData().setScale(2f);
        claimTileButton.getStyle().disabledFontColor = Color.GRAY;

        int width = Settings.screenWidth / 10;
        int height = Settings.screenHeight / 10;
        int margin = 20;

        int x = Settings.screenWidth - (margin * 2) - width;
        int y = Settings.screenHeight - (margin * 2) - height;

        claimTileButton.setPosition(x, y);
        shopButton.setPosition(x, y - (height) - (margin));
        attackButton.setPosition(x, y - (height * 2) - (margin * 2));
        scoutButton.setPosition(x, y - (height * 3) - (margin * 3));
        cardButton.setPosition(x, y - (height * 4) - (margin * 4));
        endButton.setPosition(x, y - (height * 5) - (margin * 5));

        claimTileButton.setSize(width, height);
        shopButton.setSize(width, height);
        attackButton.setSize(width, height);
        scoutButton.setSize(width, height);
        cardButton.setSize(width, height);
        endButton.setSize(width, height);

        addActor(claimTileButton);
        addActor(shopButton);
        addActor(attackButton);
        addActor(scoutButton);
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
                popupSelector = new CardSelector();
                popupSelector.animateEntrance();
                addActor(popupSelector);
            }
        });

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (popupSelector != null) {
            popupSelector.update();
            if (popupSelector.canRemove()) popupSelector = null;
        }
    }

    public void disableInput() {
        claimTileButton.setDisabled(true);
        shopButton.setDisabled(true);
        attackButton.setDisabled(true);
        scoutButton.setDisabled(true);
        cardButton.setDisabled(true);
        endButton.setDisabled(true);
    }

    public void enableInput() {
        claimTileButton.setDisabled(false);
        shopButton.setDisabled(false);
        attackButton.setDisabled(false);
        scoutButton.setDisabled(false);
        cardButton.setDisabled(false);
        endButton.setDisabled(false);
    }

    public boolean isDisabled() { return claimTileButton.isDisabled(); }



}
