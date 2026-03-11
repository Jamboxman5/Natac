package me.jamboxman5.natac;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.jamboxman5.natac.net.ClientManager;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.screen.ui.Fonts;

import java.util.ArrayList;
import java.util.List;

public class Natac extends Game {

    public static Natac instance;

    DiscreteServer server;
    ClientManager clientManager;

    @Override
    public void create() {

        instance = this;

        clientManager = new ClientManager(this);
        this.setScreen(new GameScreen(new ArrayList<Player>()));

        Fonts.initFonts();

    }

    @Override
    public void render() {
        super.render(); // important!

    }

    @Override
    public void dispose() {
        Fonts.dispose();
    }
}
