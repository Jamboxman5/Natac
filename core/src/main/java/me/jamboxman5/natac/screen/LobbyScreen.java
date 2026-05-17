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
import me.jamboxman5.natac.map.MapBuilder;
import me.jamboxman5.natac.net.packet.PacketStartGame;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.ui.Fonts;
import me.jamboxman5.natac.util.Settings;


import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class LobbyScreen implements Screen {

    Texture menuBKG;

    OrthographicCamera camera;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapes;

    private final Stage uiStage;

    private final Viewport viewport;

    public LobbyScreen(Player player) {

        Natac.instance.player = player;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.screenWidth, Settings.screenHeight);

        viewport = new FitViewport(Settings.screenWidth, Settings.screenHeight, camera);
        uiStage = new Stage(viewport);

        shapes = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

        TextButton button1 = new TextButton("Start Game", skin);
        TextButton button2 = new TextButton("Quit Game", skin);

        button1.setSize(200,80);
        button2.setSize(200,80);
        button1.setPosition(Settings.screenWidth - 240, 140);
        button2.setPosition(Settings.screenWidth - 240, 40);

        button1.getStyle().font.getData().setScale(2f);

        if (Natac.instance.isHosting()) uiStage.addActor(button1);
        uiStage.addActor(button2);

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PacketStartGame packet = new PacketStartGame();
                packet.map = MapBuilder.generateMap(5);
                Natac.instance.getClientManager().sendPacketTCP(packet);
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Natac.instance.getClientManager().disconnect(player);
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
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 3f,  "Lobby", spriteBatch, 40f, Settings.screenHeight - 60);
        Fonts.PLACEHOLDER_FONT.draw(spriteBatch, "Players: ", 40f, 250);

        int y = 200;
        for (UUID id : Natac.instance.getClientManager().getConnectedPlayers()) {
            String name = Natac.instance.getClientManager().getConnectedPlayerName(id);
            Fonts.PLACEHOLDER_FONT.draw(spriteBatch, name, 40, y);
            y -= 30;
        }

        spriteBatch.end();
    }

}
