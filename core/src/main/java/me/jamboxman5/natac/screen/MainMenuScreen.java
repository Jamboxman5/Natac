package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
import java.util.ArrayList;
import java.util.Set;

public class MainMenuScreen implements Screen {

    Texture menuBKG;

    OrthographicCamera camera;

    int move = 600;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapes;

    private final Stage uiStage;

    private final Viewport viewport;


    public MainMenuScreen() {

        Natac.instance.player = null;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.screenWidth, Settings.screenHeight);

        viewport = new FitViewport(Settings.screenWidth, Settings.screenHeight, camera);
        uiStage = new Stage(viewport);

        shapes = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        Vector2 center = new Vector2((float) Settings.screenWidth /2, (float) Settings.screenHeight /2);

        Skin skin = new Skin(Gdx.files.internal("ui/skins/expee/expee-ui.json"));

        TextButton button1 = new TextButton("Host Game", skin);
        TextButton button2 = new TextButton("Join Game", skin);
        TextField field = new TextField("", skin);

        uiStage.addActor(button1);
        uiStage.addActor(button2);
        uiStage.addActor(field);

        field.setAlignment(Align.center);
        field.setPosition(center.x - 160, center.y);
        field.setSize(195, 40);
        field.getStyle().font.getData().setScale(1.2f);

        SelectBox<PlayerClass> classSelectBox = new SelectBox<>(skin);
        classSelectBox.setItems(PlayerClass.values());
        classSelectBox.setSize(120, 40);
        classSelectBox.setPosition(center.x + 40, center.y);
        uiStage.addActor(classSelectBox);

        button1.setSize(90, 40);
        button2.setSize(90, 40);

        button1.setPosition(center.x - 100, center.y - 60);
        button2.setPosition(center.x + 100 - button2.getWidth(), center.y - 60);

        field.setMessageText("Enter name: ");

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (field.getText().isEmpty()) return;
                Player player = new Player(field.getText(), classSelectBox.getSelected(), Color.RED);
                Natac.instance.hostGame(player);
                Natac.instance.setScreen(new LobbyScreen(player));
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (field.getText().isEmpty()) return;
                String ip = JOptionPane.showInputDialog("Enter host IP: ");
                Player player = new Player(field.getText(), classSelectBox.getSelected(), Color.RED);

                if (Natac.instance.joinGame(player, ip)) Natac.instance.setScreen(new LobbyScreen(player));
                else Natac.instance.getClientManager().logSevere("INVALID ADDRESS!");

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
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 3f, "NATAC", spriteBatch, Fonts.getXForCenteredText(Settings.screenWidth / 2, "NATAC", Fonts.PLACEHOLDER_FONT, 3f), 550);
        spriteBatch.end();
    }


}
