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
import me.jamboxman5.natac.screen.MainMenuScreen;
import me.jamboxman5.natac.screen.ui.Fonts;

import javax.swing.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Natac extends Game {

    public static Natac instance;

    DiscreteServer server;
    ClientManager clientManager;

    public Player player;

    @Override
    public void create() {

        instance = this;

        clientManager = new ClientManager(this);

        this.setScreen(new MainMenuScreen());

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

    public void hostGame() {
        server = new DiscreteServer();
    }

    public boolean joinGame(Player player, String hostIP) {
        try {
            clientManager.connect(player, hostIP);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public ClientManager getClientManager() { return clientManager; }
}
