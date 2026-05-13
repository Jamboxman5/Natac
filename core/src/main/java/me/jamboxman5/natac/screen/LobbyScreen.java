package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.ui.Fonts;
import me.jamboxman5.natac.util.Settings;


import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class LobbyScreen implements Screen {

    Texture menuBKG;

    OrthographicCamera camera;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapes;

    private final Stage uiStage;
    private final Table elements;

    private final Viewport viewport;

    public LobbyScreen(Player player) {

        Natac.instance.player = player;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.screenWidth, Settings.screenHeight);

        viewport = new FitViewport(1280, 720, camera);
        uiStage = new Stage(viewport);

        shapes = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        elements = new Table();
        elements.setFillParent(true);

        uiStage.addActor(elements);

        Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

        TextButton button1 = new TextButton("Start Game", skin);
        TextButton button2 = new TextButton("Quit Game", skin);

        elements.add(button1);
        elements.add(button2);
        elements.row();

        elements.setDebug(false);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.setScreen(new GameScreen(player, new ArrayList<>(), "localhost"));
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.setScreen(new MainMenuScreen());
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
        spriteBatch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);


        update(delta);
        draw();
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



    public void update(float delta) {
        uiStage.act(delta);
    }

    public void drawBKG(SpriteBatch batch) {
        batch.begin();
//        batch.draw(menuBKG, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    public void drawTitle(SpriteBatch batch) {
        batch.begin();

        batch.end();
    }

    public void draw() {

        drawBKG(spriteBatch);
        drawTitle(spriteBatch);
        uiStage.draw();
        spriteBatch.begin();
        Fonts.PLACEHOLDER_FONT.draw(spriteBatch, "Lobby", Fonts.getXForCenteredText(Settings.screenWidth / 2, "NATAC", Fonts.PLACEHOLDER_FONT), 500);
        Fonts.PLACEHOLDER_FONT.draw(spriteBatch, "Players: ", Fonts.getXForCenteredText(Settings.screenWidth / 2, "NATAC", Fonts.PLACEHOLDER_FONT), 200);

        int y = 200;
        for (String id : Natac.instance.getClientManager().getConnectedPlayers()) {
            String name = Natac.instance.getClientManager().getConnectedPlayerName(id);
            Fonts.PLACEHOLDER_FONT.draw(spriteBatch, name, Fonts.getXForCenteredText(Settings.screenWidth / 2, name, Fonts.PLACEHOLDER_FONT) + 100, y);
            y -= 30;
        }

        spriteBatch.end();
    }

}
