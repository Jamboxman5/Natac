package me.jamboxman5.natac.screen.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.screen.MainMenuScreen;
import me.jamboxman5.natac.screen.SettingsScreen;
import me.jamboxman5.natac.screen.ui.Fonts;
import me.jamboxman5.natac.util.Settings;

public class SettingsStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));
    Vector2 center = new Vector2((float) Settings.screenWidth /2, (float) Settings.screenHeight /2);

    TextButton button1 = new TextButton("< Back", skin);
    TextButton button2 = new TextButton("Apply", skin);

    Slider musVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
    Slider sfxVolumeSlider = new Slider(0f, 1f, 0.01f, false, skin);

    SelectBox<Settings.Resolution> resolutionSelector = new SelectBox<>(skin);
    SelectBox<Integer> fowRadiusSelector = new SelectBox<>(skin);

    BitmapFont labelFont = Fonts.createFont("placeholder", 40, Color.WHITE);

    public SettingsStage() {
        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));


        button1.getStyle().font.getData().setScale(1.2f);

        addActor(button1);
        addActor(button2);

        addActor(musVolumeSlider);
        addActor(sfxVolumeSlider);

        float yStart = center.y + (40 * ((getActors().size-2)/2f));

        resolutionSelector.setItems(Settings.resolutions);
        resolutionSelector.setSize(120, 40);
        resolutionSelector.setPosition(center.x + 300 - resolutionSelector.getWidth(), yStart);
        resolutionSelector.setSelected(Settings.getResolution());
        addActor(resolutionSelector);

        yStart-=50;

        fowRadiusSelector.setItems(1, 2, 3);
        fowRadiusSelector.setSize(120, 40);
        fowRadiusSelector.setPosition(center.x + 300 - fowRadiusSelector.getWidth(), yStart);
        fowRadiusSelector.setSelected(Settings.defogTileRadius);
        addActor(fowRadiusSelector);
        yStart-=50;

        button1.setSize(90, 40);
        button2.setSize(90, 40);

        sfxVolumeSlider.setSize(200, 40);
        musVolumeSlider.setSize(200, 40);

        button1.setPosition(40, 40);
        yStart-=50;
        button2.setPosition(Settings.screenWidth - 40 - button2.getWidth(), 40);
        yStart-=50;

        sfxVolumeSlider.setPosition(center.x + 300 - sfxVolumeSlider.getWidth(), yStart);
        yStart-=50;
        musVolumeSlider.setPosition(center.x + 300 - musVolumeSlider.getWidth(), yStart);

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.setScreen(new MainMenuScreen());
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setResolution(resolutionSelector.getSelected());
                Settings.defogTileRadius = fowRadiusSelector.getSelected();
                Natac.instance.setScreen(new SettingsScreen());
            }
        });
    }

    public void draw(SpriteBatch batch) {
        super.draw();

        labelFont.draw(batch, "Resolution: ", center.x - 300, resolutionSelector.getY() + Fonts.getTextHeight("Resolution: ", labelFont, 1f));
        labelFont.draw(batch, "Defog Radius: ", center.x - 300, fowRadiusSelector.getY() + Fonts.getTextHeight("Defog Radius: ", labelFont, 1f));

    }

}
