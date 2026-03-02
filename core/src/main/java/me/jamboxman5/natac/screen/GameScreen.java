package me.jamboxman5.natac.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.MapBuilder;
import me.jamboxman5.natac.player.Player;

import java.util.List;

public class GameScreen implements Screen {

    private Map map;
    private List<Player> players;

    SpriteBatch batch;

    public GameScreen(List<Player> players) {
        this.players = players;
        map = MapBuilder.generateMap(3);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float v) {
        map.draw(batch);
        for (Player p : players) p.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
}
