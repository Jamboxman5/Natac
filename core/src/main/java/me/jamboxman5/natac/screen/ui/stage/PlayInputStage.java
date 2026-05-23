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
import me.jamboxman5.natac.util.Settings;

public class PlayInputStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

    TextButton claimTileButton = new TextButton("Claim", skin);
    TextButton shopButton = new TextButton("Shop", skin);
    TextButton buildButton = new TextButton("Build", skin);
    TextButton attackButton = new TextButton("Attack", skin);
    TextButton scoutButton = new TextButton("Scout", skin);
    TextButton tradeButton = new TextButton("Trade", skin);

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
        buildButton.setPosition(x, y - (height * 2) - (margin * 2));
        attackButton.setPosition(x, y - (height * 3) - (margin * 3));
        scoutButton.setPosition(x, y - (height * 4) - (margin * 4));
        tradeButton.setPosition(x, y - (height * 5) - (margin * 5));

        claimTileButton.setSize(width, height);
        shopButton.setSize(width, height);
        buildButton.setSize(width, height);
        attackButton.setSize(width, height);
        scoutButton.setSize(width, height);
        tradeButton.setSize(width, height);

        addActor(claimTileButton);
        addActor(shopButton);
        addActor(buildButton);
        addActor(attackButton);
        addActor(scoutButton);
        addActor(tradeButton);

        disableInput();

//        claimTileButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                Natac.instance.getClientManager().selectFromConnectedPlayers();
//            }
//        });
    }

    public void disableInput() {
        claimTileButton.setDisabled(true);
        shopButton.setDisabled(true);
        buildButton.setDisabled(true);
        attackButton.setDisabled(true);
        scoutButton.setDisabled(true);
        tradeButton.setDisabled(true);
    }

    public void enableInput() {
        claimTileButton.setDisabled(false);
        shopButton.setDisabled(false);
        buildButton.setDisabled(false);
        attackButton.setDisabled(false);
        scoutButton.setDisabled(false);
        tradeButton.setDisabled(false);
    }

    public boolean isDisabled() { return claimTileButton.isDisabled(); }



}
