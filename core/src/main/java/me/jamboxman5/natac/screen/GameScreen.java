package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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

    private final Vector2 touchPos = new Vector2();

    SpriteBatch batch;
    ShapeRenderer shapes;

    public GameScreen(List<Player> players) {
        this.players = players;
        map = MapBuilder.generateMap(3);

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, gameCamera);

        Gdx.input.setInputProcessor(this);



    }

    private void draw() {
        shapes.setProjectionMatrix(gameCamera.combined);

        map.draw(gameCamera, batch, shapes);
        for (Player p : players) p.draw();
    }

    private void update(float delta) {
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);

        map.update(touchPos);

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
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
