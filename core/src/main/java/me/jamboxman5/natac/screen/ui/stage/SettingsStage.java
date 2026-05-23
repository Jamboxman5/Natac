package me.jamboxman5.natac.screen.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.screen.MainMenuScreen;
import me.jamboxman5.natac.screen.SettingsScreen;
import me.jamboxman5.natac.util.Settings;

public class SettingsStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

    public SettingsStage() {
        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));

        Vector2 center = new Vector2((float) Settings.screenWidth /2, (float) Settings.screenHeight /2);


        TextButton button1 = new TextButton("< Back", skin);
        TextButton button2 = new TextButton("Apply", skin);
        button1.getStyle().font.getData().setScale(1.2f);

        addActor(button1);
        addActor(button2);

        SelectBox<Settings.Resolution> resolutions = new SelectBox<>(skin);
        resolutions.setItems(Settings.resolutions);
        resolutions.setSize(120, 40);
        resolutions.setPosition(center.x + 40, center.y);
        resolutions.setSelected(Settings.getResolution());
        addActor(resolutions);

        button1.setSize(90, 40);
        button2.setSize(90, 40);

        button1.setPosition(40, 40);
        button2.setPosition(Settings.screenWidth - 40 - button2.getWidth(), 40);

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.setScreen(new MainMenuScreen());
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setResolution(resolutions.getSelected());
                Natac.instance.setScreen(new SettingsScreen());
            }
        });
    }

}
