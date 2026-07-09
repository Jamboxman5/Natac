package me.jamboxman5.natac.screen.ui.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.data.SettingsData;
import me.jamboxman5.natac.screen.MainMenuScreen;
import me.jamboxman5.natac.screen.SettingsScreen;
import me.jamboxman5.natac.screen.ui.Fonts;
import me.jamboxman5.natac.util.Settings;

public class SettingsStage extends Stage {

    Skin skin = new Skin(Gdx.files.internal("ui/skins/shade/uiskin.json"));
    Vector2 center = new Vector2((float) Settings.screenWidth /2, (float) Settings.screenHeight /2);

    TextButton button1 = new TextButton("< Back", skin);
    TextButton button2 = new TextButton("Apply", skin);

    Slider musVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
    Slider sfxVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
    Slider defogRadiusSlider = new Slider(1, 3, 1, false, skin);
    Slider mapRadiusSlider = new Slider(1, 6, 1, false, skin);

    Slider maxPlayersSlider = new Slider(1, 6, 1, false, skin);
    Slider botDelaySlider = new Slider(0, 10, 1, false, skin);
    Slider botDelayRandomnessSlider = new Slider(0, 1, 0.01f, false, skin);

    SelectBox<Settings.Resolution> resolutionSelector = new SelectBox<>(skin);

    BitmapFont labelFont = Fonts.createFont("placeholder", 40, Color.WHITE);

    float currentMusVol = Settings.musVolume;
    float currentSfxVol = Settings.sfxVolume;

    public SettingsStage() {
        super(new FitViewport(Settings.screenWidth, Settings.screenHeight));


        button1.getStyle().font.getData().setScale(1.2f);

        addActor(button1);
        addActor(button2);

        addActor(musVolumeSlider);
        addActor(sfxVolumeSlider);
        addActor(defogRadiusSlider);
        addActor(mapRadiusSlider);

        addActor(maxPlayersSlider);
        addActor(botDelaySlider);
        addActor(botDelayRandomnessSlider);

        float yStart = center.y + (40 * ((getActors().size-2)/2f));

        resolutionSelector.setItems(Settings.resolutions);
        resolutionSelector.setSize(120, 40);
        resolutionSelector.setPosition(center.x + 300 - resolutionSelector.getWidth(), yStart);
        resolutionSelector.setSelected(Settings.getResolution());
        addActor(resolutionSelector);

        yStart-=50;

        button1.setSize(90, 40);
        button2.setSize(90, 40);

        sfxVolumeSlider.setSize(200, 20);
        musVolumeSlider.setSize(200, 20);
        defogRadiusSlider.setSize(200, 20);
        mapRadiusSlider.setSize(200, 20);

        maxPlayersSlider.setSize(200, 20);
        botDelaySlider.setSize(200, 20);
        botDelayRandomnessSlider.setSize(200, 20);

        button1.setPosition(40, 40);
        button2.setPosition(Settings.screenWidth - 40 - button2.getWidth(), 40);

        defogRadiusSlider.setPosition(center.x + 300 - defogRadiusSlider.getWidth(), yStart);
        yStart-=50;

        sfxVolumeSlider.setPosition(center.x + 300 - sfxVolumeSlider.getWidth(), yStart);
        yStart-=50;
        musVolumeSlider.setPosition(center.x + 300 - musVolumeSlider.getWidth(), yStart);
        yStart-=50;
        mapRadiusSlider.setPosition(center.x + 300 - musVolumeSlider.getWidth(), yStart);

        yStart-=50;
        maxPlayersSlider.setPosition(center.x + 300 - musVolumeSlider.getWidth(), yStart);
        yStart-=50;
        botDelaySlider.setPosition(center.x + 300 - musVolumeSlider.getWidth(), yStart);
        yStart-=50;
        botDelayRandomnessSlider.setPosition(center.x + 300 - musVolumeSlider.getWidth(), yStart);

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setMusicVolume(currentMusVol);
                Natac.instance.setScreen(new MainMenuScreen());
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setResolution(resolutionSelector.getSelected());
                Settings.defogTileRadius = (int) defogRadiusSlider.getValue();
                Settings.mapRadius = (int) mapRadiusSlider.getValue();

                Settings.setMusicVolume(musVolumeSlider.getValue());
                Settings.setSfxVolume(sfxVolumeSlider.getValue());

                Settings.botDelayRandom = botDelayRandomnessSlider.getValue();
                Settings.botDelayMS = (int) (botDelaySlider.getValue() * 1000);

                Settings.maxPlayers = (int) maxPlayersSlider.getValue();

                Natac.instance.setScreen(new SettingsScreen());

                SettingsData.updateSettings();
            }
        });

        sfxVolumeSlider.setValue(Settings.sfxVolume);
        musVolumeSlider.setValue(Settings.musVolume);
        defogRadiusSlider.setValue(Settings.defogTileRadius);
        mapRadiusSlider.setValue(Settings.mapRadius);

        botDelayRandomnessSlider.setValue(Settings.botDelayRandom);
        botDelaySlider.setValue(Settings.botDelayMS / 1000f);

        maxPlayersSlider.setValue(Settings.maxPlayers);

        musVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setMusicVolume(musVolumeSlider.getValue());
            }
        });

    }

    public void draw(SpriteBatch batch) {
        super.draw();

        labelFont.draw(batch, "Resolution: ", center.x - 300, resolutionSelector.getY() + Fonts.getTextHeight("Resolution: ", labelFont, 1f));
        labelFont.draw(batch, "Defog Radius: ", center.x - 300, defogRadiusSlider.getY() + Fonts.getTextHeight("Defog Radius: ", labelFont, 1f));
        labelFont.draw(batch, "Music Volume: ", center.x - 300, musVolumeSlider.getY() + Fonts.getTextHeight("Music Volume:", labelFont, 1f));
        labelFont.draw(batch, "SFX Volume: ", center.x - 300, sfxVolumeSlider.getY() + Fonts.getTextHeight("SFX Volume: ", labelFont, 1f));
        labelFont.draw(batch, "Map Radius: ", center.x - 300, mapRadiusSlider.getY() + Fonts.getTextHeight("Map Radius: ", labelFont, 1f));
        labelFont.draw(batch, "Max Players: ", center.x - 300, maxPlayersSlider.getY() + Fonts.getTextHeight("Map Radius: ", labelFont, 1f));
        labelFont.draw(batch, "Bot Delay: ", center.x - 300, botDelaySlider.getY() + Fonts.getTextHeight("Map Radius: ", labelFont, 1f));
        labelFont.draw(batch, "Bot Delay Randomness: ", center.x - 300, botDelayRandomnessSlider.getY() + Fonts.getTextHeight("Map Radius: ", labelFont, 1f));

    }

}
