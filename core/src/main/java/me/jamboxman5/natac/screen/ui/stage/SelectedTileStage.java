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

import java.util.Set;

public class SelectedTileStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));

    TextButton buildButton = new TextButton("Build", skin);
    TextButton backButton = new TextButton("Back", skin);

    public SelectedTileStage() {

        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        buildButton.getStyle().font.getData().setScale(2f);
        buildButton.getStyle().disabledFontColor = Color.GRAY;

        int width = Settings.screenWidth / 10;
        int height = Settings.screenHeight / 10;
        int margin = 20;

        float x = (Settings.screenWidth / 2f) - (margin / 2f) - width;

        backButton.setPosition(x, margin);
        buildButton.setPosition(x + width + margin, margin);

        buildButton.setSize(width, height);
        backButton.setSize(width, height);

        addActor(buildButton);
        addActor(backButton);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.getGame().getMap().deselectTile();
            }
        });

        buildButton.setDisabled(false);
        backButton.setDisabled(false);

    }

}
