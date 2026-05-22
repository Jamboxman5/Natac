package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.screen.ui.Fonts;
import me.jamboxman5.natac.util.Settings;

import javax.swing.*;

public class SettingsScreen implements Screen {

    Texture menuBKG;

    OrthographicCamera camera;

    int move = 600;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapes;

    private final Stage uiStage;

    private final Viewport viewport;

    private final BitmapFont font = Fonts.createFont("placeholder", 76, Color.WHITE);

    public SettingsScreen() {

        Natac.instance.player = null;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.screenWidth, Settings.screenHeight);

        viewport = new FitViewport(Settings.screenWidth, Settings.screenHeight, camera);
        uiStage = new Stage(viewport);

        shapes = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        Vector2 center = new Vector2((float) Settings.screenWidth /2, (float) Settings.screenHeight /2);

        Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

        TextButton button1 = new TextButton("< Back", skin);
        TextButton button2 = new TextButton("Apply", skin);
        button1.getStyle().font.getData().setScale(1.2f);

        uiStage.addActor(button1);
        uiStage.addActor(button2);

        SelectBox<Settings.Resolution> resolutions = new SelectBox<>(skin);
        resolutions.setItems(Settings.resolutions);
        resolutions.setSize(120, 40);
        resolutions.setPosition(center.x + 40, center.y);
        resolutions.setSelected(Settings.getResolution());
        uiStage.addActor(resolutions);

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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiStage);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0, 0f, 1);

        camera.update();
        shapes.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);

//        Fonts.drawScaled(Fonts.SUBTITLEFONT, .85f, "Zombie Assault", game.batch,20, ScreenInfo.HEIGHT - Fonts.TITLEFONT.getScaleY() - Fonts.SUBTITLEFONT.getScaleY() - 220);

        update(delta);
        draw();


    }

    public void update(float delta) {
        uiStage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        menuBKG.dispose();
    }

    public void draw() {
        uiStage.draw();

        spriteBatch.begin();
        font.draw(spriteBatch, "Settings", 40f, Settings.screenHeight - 40);
        spriteBatch.end();
    }


}
