package me.jamboxman5.natac.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.MapBuilder;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.ui.Fonts;
import me.jamboxman5.natac.screen.ui.UIManager;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class GameScreen implements Screen, InputProcessor {

    private Map map;
    private List<Player> players;

    public static Player player;

    private final OrthographicCamera gameCamera;
    private final OrthographicCamera uiCamera;
    private final Viewport viewport;
    private float targetZoom = 1.0f;
    private final Vector2 targetPos = new Vector2(640, 360);
    private final Vector2 mousePos = new Vector2();
    private final Vector2 clickPos = new Vector2();
    private final Vector2 lastMousePos = new Vector2();

    private InputMultiplexer multiplexer;

    SpriteBatch batch;
    ShapeDrawer shapes;

    SpriteBatch uiSprites;
    ShapeDrawer uiShapes;

    private Stage uiStage;

    private static State gameState;

    public static State getState() { return gameState; }
    public static void setState(State newState) { gameState = newState; }

    public enum State {
        CLAIM, WAIT, PLAY;
    }

    public GameScreen(Player player, List<Player> others) {
        this.players = others;
        GameScreen.player = player;

        map = MapBuilder.generateMap(3);

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera(Settings.screenWidth, Settings.screenHeight);
        uiCamera.setToOrtho(false);
        viewport = new FitViewport(1280, 720, gameCamera);
        uiStage = new Stage(viewport);


        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(uiStage);

        Gdx.input.setInputProcessor(multiplexer);

    }

    private void draw() {
        batch.setProjectionMatrix(gameCamera.combined);
        uiSprites.setProjectionMatrix(uiCamera.combined);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        map.draw(gameCamera, batch, shapes);
        for (Player p : players) p.draw();
        batch.end();

        uiSprites.begin();
        UIManager.draw(uiSprites, uiShapes, gameState);
        uiSprites.end();

        uiStage.draw();


    }


    private void update(float delta) {

        uiStage.act(delta);

        gameCamera.zoom = MathUtils.lerp(gameCamera.zoom, targetZoom, 0.15f);
        gameCamera.position.x = MathUtils.clamp(MathUtils.lerp(gameCamera.position.x, targetPos.x, .15f), 0, 1280);
        gameCamera.position.y = MathUtils.clamp(MathUtils.lerp(gameCamera.position.y, targetPos.y, .15f), 0, 720);

        gameCamera.update();
        uiCamera.update();

        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos);

        map.update(mousePos);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        uiSprites = new SpriteBatch();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion whitePixel = new TextureRegion(texture);
        pixmap.dispose();

        shapes = new ShapeDrawer(batch, whitePixel);
        uiShapes = new ShapeDrawer(uiSprites, whitePixel);

        gameState = State.CLAIM;
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
        uiStage.dispose();
        uiSprites.dispose();
        batch.dispose();
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
        lastMousePos.set(screenX, screenY);
        viewport.unproject(lastMousePos);

        map.clickTile(lastMousePos);
        return true;
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

        Vector2 newTouchPos = new Vector2(screenX, screenY);
        viewport.unproject(newTouchPos);

        float deltaX = newTouchPos.x - lastMousePos.x;
        float deltaY = newTouchPos.y - lastMousePos.y;

        targetPos.x -= deltaX;
        targetPos.y -= deltaY;

        lastMousePos.set(screenX, screenY);
        viewport.unproject(lastMousePos);

        return true;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    @Override
    public boolean scrolled(float amountX, float amountY) {
        float worldMouseXBefore = mousePos.x;
        float worldMouseYBefore = mousePos.y;

        targetZoom = MathUtils.clamp(targetZoom + (amountY * 0.2f), 0.2f, 3.0f);

        float zoomRatio = targetZoom / gameCamera.zoom;

        targetPos.x = worldMouseXBefore + (gameCamera.position.x - worldMouseXBefore) * zoomRatio;
        targetPos.y = worldMouseYBefore + (gameCamera.position.y - worldMouseYBefore) * zoomRatio;

        return true;
    }
}
