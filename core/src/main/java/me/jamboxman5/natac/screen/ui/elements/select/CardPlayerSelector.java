package me.jamboxman5.natac.screen.ui.elements.select;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.cards.Card;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.util.Settings;

public class CardPlayerSelector extends Window {

    static Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    public CardPlayerSelector(Card playing) {
        super("Select: ", skin);
        setSize(Settings.screenWidth / 2f, Settings.screenHeight / 2f);
        setPosition(getWidth() /2f, getHeight() /2f);
        setMovable(false);
        setModal(true);

        SelectBox<Player> selectBox = new SelectBox<>(skin);
        selectBox.setItems(Natac.instance.getClientManager().getConnectedPlayers());
        selectBox.setSize(600, 100);
        selectBox.setPosition(getWidth() / 2f - selectBox.getWidth() / 2f, getHeight()/2f);
        addActor(selectBox);

        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playing.playCard(selectBox.getSelected(), Natac.instance.getGame().getMap());
                Natac.instance.player.removeCard(playing);
                remove();
            }
        });
        confirmButton.setSize(200, 100);
        confirmButton.setPosition(400, 0);
        add(confirmButton);

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });
        confirmButton.setSize(200, 100);
        confirmButton.setPosition(600, 0);
        add(closeButton);


    }

}
