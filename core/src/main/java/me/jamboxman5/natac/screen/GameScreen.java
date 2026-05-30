package me.jamboxman5.natac.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.screen.ui.stage.PlayInputStage;
import me.jamboxman5.natac.screen.ui.UIManager;
import me.jamboxman5.natac.screen.ui.stage.SelectedTileStage;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.JoinType;
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
    private SelectedTileStage tileStage;

    private State gameState;
    private SelectionState tileSelectState;

    private Sprite selectedTileSprite = null;
    private Polygon selectedTileHighlight = null;

    public enum State {
        CLAIM, WAIT, PLAY;
    }

    public enum SelectionState {
        OWNED, NEIGHBORING, NONE, BASE,
    }

    public void setTileSelectState(SelectionState state) {
        this.tileSelectState = state;
    }

    public GameScreen(Map map) {

        this.map = map;

        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera(Settings.screenWidth, Settings.screenHeight);
        uiCamera.setToOrtho(false);
        viewport = new FitViewport(1280, 720, gameCamera);
        uiStage = new PlayInputStage();
        tileStage = new SelectedTileStage();


        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(tileStage);
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
        else tileStage.draw();


        uiSprites.begin();
        UIManager.draw(uiSprites, uiShapes, gameState);
        uiSprites.end();

        if (map.getSelectedTile() != null) {
            modalBatch.begin();
            drawSelectedTileMenu(map.getSelectedTile());
            modalBatch.end();
        } else selectedTileSprite = null;



    }


    private void update(float delta) {

        if (gameState.equals(State.PLAY)) {
            if (uiStage.isDisabled()) uiStage.enableInput();
        } else {
            if (!uiStage.isDisabled()) uiStage.disableInput();
        }

        if (map.getSelectedTile() == null) uiStage.act(delta);
        else tileStage.act(delta);

        gameCamera.zoom = MathUtils.lerp(gameCamera.zoom, targetZoom, 0.15f);
        gameCamera.position.x = MathUtils.clamp(MathUtils.lerp(gameCamera.position.x, targetPos.x, .15f), 0, 1280);
        gameCamera.position.y = MathUtils.clamp(MathUtils.lerp(gameCamera.position.y, targetPos.y, .15f), 0, 720);

        gameCamera.update();
        uiCamera.update();

        if (map.getSelectedTile() == null) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(mousePos);

            map.update(mousePos);
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
        tileSelectState = SelectionState.BASE;

        vfxManager = new VfxManager(Pixmap.Format.RGBA8888);

        blur = new GaussianBlurEffect();
        blur.setAmount(3);
        blur.setPasses(4);

        vfxManager.addEffect(blur);

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

    private void drawSelectedTileMenu(Tile t) {
        if (selectedTileSprite == null) {
            selectedTileSprite = new Sprite(t.getSprite());
            selectedTileHighlight = generateHighlight();
        }

        selectedTileSprite.setCenter(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);
        selectedTileSprite.setScale(5f * 0.9f, 5f * 0.9f);
        selectedTileSprite.draw(modalBatch);

        modalShapes.setDefaultLineWidth(10f);
        modalShapes.setColor(Color.WHITE);
        modalShapes.polygon(selectedTileHighlight, JoinType.POINTY);
    }

    private Polygon generateHighlight() {
        float[] vertices = new float[12];
        for (int i = 0; i < 6; i++) {
            float angle = i * MathUtils.PI / 3;
            // Multiply the X-offset by the stretch factor
            float stretchFactor = 1.5f;
            int radius = 250;
            vertices[i * 2] = (radius * MathUtils.cos(angle) * stretchFactor);
            vertices[i * 2 + 1] = (radius * MathUtils.sin(angle));
        }
        Polygon shape = new Polygon(vertices);
        shape.setPosition(Settings.screenWidth / 2f, (Settings.screenHeight / 2f) + 50);
        shape.setOrigin(0,0);
        return shape;
    }

}
