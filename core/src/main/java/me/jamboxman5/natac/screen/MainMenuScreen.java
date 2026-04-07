package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
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
import java.util.ArrayList;

public class MainMenuScreen implements Screen {

    Texture menuBKG;

    OrthographicCamera camera;

    int move = 600;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapes;

    private final Stage uiStage;
    private final Table elements;

    private final Viewport viewport;


    public MainMenuScreen() {

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

        TextButton button1 = new TextButton("Host Game", skin);
        TextButton button2 = new TextButton("Join Game", skin);
        TextField field = new TextField("", skin);

        elements.add(field).fillX().center();
        elements.row();
        elements.add(button1);
        elements.add(button2);
        elements.row();
        field.setMessageText("Enter name: ");

        elements.setDebug(true);
        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (field.getText().isEmpty()) return;
                Natac.instance.setScreen(new GameScreen(new Player(field.getText(), Color.RED), new ArrayList<>(), "localhost"));
            }
        });

        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (field.getText().isEmpty()) return;
                String ip = JOptionPane.showInputDialog("Enter host IP: ");
                Natac.instance.setScreen(new GameScreen(new Player(field.getText(), Color.RED), new ArrayList<>(), ip));
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
        Fonts.PLACEHOLDER_FONT.draw(spriteBatch, "NATAC", Fonts.getXForCenteredText(Settings.screenWidth / 2, "NATAC", Fonts.PLACEHOLDER_FONT), 500);
        spriteBatch.end();
    }


}
