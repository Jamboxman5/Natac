package me.jamboxman5.natac.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.jamboxman5.natac.util.Settings;

import java.util.Set;

public class PlayInputStage extends Stage {

    public PlayInputStage() {
        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

        TextButton claimTileButton = new TextButton("Claim", skin);
        TextButton shopButton = new TextButton("Shop", skin);
        TextButton attackButton = new TextButton("Attack", skin);
        TextButton scoutButton = new TextButton("Scout", skin);
        TextButton tradeButton = new TextButton("Trade", skin);

        claimTileButton.getStyle().font.getData().setScale(2f);

        int width = Settings.screenWidth / 10;
        int height = Settings.screenHeight / 10;
        int margin = 20;

        int x = Settings.screenWidth - (margin * 2) - width;
        int y = Settings.screenHeight - (margin * 2) - height;

        claimTileButton.setPosition(x, y);
        shopButton.setPosition(x, y - (height) - (margin));
        attackButton.setPosition(x, y - (height * 2) - (margin * 2));
        scoutButton.setPosition(x, y - (height * 3) - (margin * 3));
        tradeButton.setPosition(x, y - (height * 4) - (margin * 4));

        claimTileButton.setSize(width, height);
        shopButton.setSize(width, height);
        attackButton.setSize(width, height);
        scoutButton.setSize(width, height);
        tradeButton.setSize(width, height);

        addActor(claimTileButton);
        addActor(shopButton);
        addActor(attackButton);
        addActor(scoutButton);
        addActor(tradeButton);

    }



}
