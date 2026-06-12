package me.jamboxman5.natac.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.screen.ui.modal.SelectedTileModal;
import me.jamboxman5.natac.screen.ui.stage.PlayInputStage;
import me.jamboxman5.natac.screen.ui.UIManager;
import me.jamboxman5.natac.sfx.MusicTracks;
import me.jamboxman5.natac.units.Unit;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GameScreen implements Screen, InputProcessor {

    private Map map;

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

    SpriteBatch modalBatch;
    ShapeDrawer modalShapes;

    SpriteBatch uiSprites;
    ShapeDrawer uiShapes;

    GaussianBlurEffect blur;
    VfxManager vfxManager;

    private PlayInputStage uiStage;

    private State gameState;
    private SelectionMode tileSelectState;

    private SelectedTileModal tileModal = null;

    public static final Vector2 viewportDimensions = new Vector2(1280, 720);

    private Unit selectedUnit = null;

    public void addUIActor(Actor actor) { uiStage.addActor(actor);
    }

    public enum State {
        CLAIM, WAIT, PLAY;
    }

    public enum SelectionMode {
        OWNED, NONE, TRAVEL, BASE,
    }

    public void setTileSelectionMode(SelectionMode state) {
        this.tileSelectState = state;
    }

    public SelectionMode getTileSelectionMode() { return tileSelectState; }

    public GameScreen(Map map) {

        this.map = map;

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera(Settings.screenWidth, Settings.screenHeight);
        uiCamera.setToOrtho(false);
        viewport = new FitViewport(viewportDimensions.x, viewportDimensions.y, gameCamera);
        uiStage = new PlayInputStage();


        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);

    }

    private void draw() {
        batch.setProjectionMatrix(gameCamera.combined);
        uiSprites.setProjectionMatrix(uiCamera.combined);
        modalBatch.setProjectionMatrix(uiCamera.combined);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        if (map.getSelectedTile() != null) vfxManager.beginInputCapture();
        else vfxManager.cleanUpBuffers();

        batch.begin();
        map.draw(gameCamera, batch, shapes, tileSelectState);
        batch.end();

        if (map.getSelectedTile() != null) {
            vfxManager.endInputCapture();
            vfxManager.applyEffects();
            vfxManager.renderToScreen();
        }

        if (map.getSelectedTile() == null) uiStage.draw();

        uiSprites.begin();
        UIManager.draw(uiSprites, uiShapes, gameState, tileSelectState);
        uiSprites.end();

        if (tileModal != null) {
            modalBatch.begin();
            tileModal.drawSelectedTileMenu(modalBatch, modalShapes);
            modalBatch.end();
        }



    }


    private void update(float delta) {

        if (gameState.equals(State.PLAY)) {
            if (uiStage.isDisabled()) uiStage.enableInput();
        } else {
            if (!uiStage.isDisabled()) uiStage.disableInput();
        }

        if (map.getSelectedTile() == null) uiStage.act(delta);
        else if (tileModal != null) tileModal.act(delta);

        gameCamera.zoom = MathUtils.lerp(gameCamera.zoom, targetZoom, 0.15f);

        targetPos.x = MathUtils.clamp(targetPos.x, (viewportDimensions.x /3f) - (1280 * (Settings.mapRadius / 3f)), (viewportDimensions.x /3f) + (1280 * (Settings.mapRadius / 3f)));
        targetPos.y = MathUtils.clamp(targetPos.y, (viewportDimensions.y /3f) - (720 * (Settings.mapRadius / 3f)), (viewportDimensions.x /3f) + (720 * (Settings.mapRadius / 3f)));

        gameCamera.position.x = MathUtils.lerp(gameCamera.position.x, targetPos.x, .15f);
        gameCamera.position.y = MathUtils.lerp(gameCamera.position.y, targetPos.y, .15f);

        gameCamera.update();
        uiCamera.update();

        if (map.getSelectedTile() == null) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(mousePos);

            map.update(mousePos);
        }

        if (map.getSelectedTile() != null) {
            if (tileModal == null) {
                tileModal = new SelectedTileModal(map.getSelectedTile());
                multiplexer.addProcessor(0, tileModal);
            } else {
                map.update();
            }
        } else {
            if (tileModal != null) {
                multiplexer.removeProcessor(tileModal);
                tileModal = null;
            }
        }

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        uiSprites = new SpriteBatch();
        modalBatch = new SpriteBatch();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion whitePixel = new TextureRegion(texture);
        pixmap.dispose();

        shapes = new ShapeDrawer(batch, whitePixel);
        uiShapes = new ShapeDrawer(uiSprites, whitePixel);
        modalShapes = new ShapeDrawer(modalBatch, whitePixel);

        gameState = State.WAIT;
        tileSelectState = SelectionMode.BASE;

        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);

        blur = new GaussianBlurEffect();
        blur.setAmount(3);
        blur.setPasses(4);

        vfxManager.addEffect(blur);

        MusicTracks.stopAll();
        MusicTracks.TEST_BGM.play();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0, 0f, 1);
        gameCamera.update();
        uiCamera.update();
        update(delta);

        draw();
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
        if (map.getSelectedTile() != null) return false;
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
        if (map.getSelectedTile() != null) return false;

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
        if (map.getSelectedTile() != null) return false;
        float worldMouseXBefore = mousePos.x;
        float worldMouseYBefore = mousePos.y;

        targetZoom = MathUtils.clamp(targetZoom + (amountY * 0.2f), 0.2f, 3.0f);

        float zoomRatio = targetZoom / gameCamera.zoom;

        targetPos.x = worldMouseXBefore + (gameCamera.position.x - worldMouseXBefore) * zoomRatio;
        targetPos.y = worldMouseYBefore + (gameCamera.position.y - worldMouseYBefore) * zoomRatio;

        return true;
    }

    public Map getMap() { return map; }

    public State getState() { return gameState; }
    public void setState(State newState) { gameState = newState; }

    public Unit getSelectedUnit() { return selectedUnit; }
    public void selectUnit(Unit unit) {
        this.selectedUnit = unit;
        this.tileSelectState = SelectionMode.TRAVEL;
        map.deselectTile();
    }

}
