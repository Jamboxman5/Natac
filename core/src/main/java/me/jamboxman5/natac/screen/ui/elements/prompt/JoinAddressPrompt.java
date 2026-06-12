package me.jamboxman5.natac.screen.ui.elements.prompt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.cards.Card;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.screen.LobbyScreen;
import me.jamboxman5.natac.util.Settings;

public class JoinAddressPrompt extends Window {

    static Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    public JoinAddressPrompt(String username, PlayerClass selectedClass) {
        super("Enter Host IP: ", skin);
        setSize(Settings.screenWidth / 2f, Settings.screenHeight / 2f);
        setPosition(getWidth() /2f, getHeight() /2f);
        setMovable(false);
        setModal(true);

        TextField addressInput = new TextField("", skin);
        addressInput.setMessageText("IP Address");
        addressInput.setSize(600, 40);
        addressInput.setPosition(getWidth() / 2f - addressInput.getWidth() / 2f, getHeight()/2f);
        addActor(addressInput);

        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (addressInput.getText().isEmpty()) return;
                Player player = new Player(username, selectedClass, Color.RED);

                if (Natac.instance.joinGame(player, addressInput.getText())) Natac.instance.setScreen(new LobbyScreen(player));
                else Natac.instance.getClientManager().logSevere("INVALID ADDRESS!");
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
