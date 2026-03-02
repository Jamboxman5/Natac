package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.MapBuilder;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.util.Settings;

import java.util.List;

public class GameScreen implements Screen, InputProcessor {

    private Map map;
    private List<Player> players;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;
    private final Viewport viewport;

    private final Vector3 touchPos = new Vector3();

    SpriteBatch batch;

    public GameScreen(List<Player> players) {
        this.players = players;
        map = MapBuilder.generateMap(3);

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();

        gameCamera.setToOrtho(false, Settings.screenWidth, Settings.screenHeight);
        gameCamera.zoom = Settings.maxZoom;
        uiCamera.setToOrtho(false, Settings.screenWidth, Settings.screenHeight);
        Gdx.input.setInputProcessor(this);

        viewport = new FitViewport(1280, 720, gameCamera);


    }

    private void draw() {
        map.draw(batch);
        for (Player p : players) p.draw();
    }

    private void update(float delta) {
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        }

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0, 0f, 1);
        gameCamera.update();
        uiCamera.update();

        draw();
        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {

        return false;
    }
}
