package me.jamboxman5.natac;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.jamboxman5.natac.net.ClientManager;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketEndTurn;
import me.jamboxman5.natac.net.packet.PacketUtil;
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

    private boolean hosting = false;
    private boolean isTurn = false;

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
        try {
            if (isHosting()) server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hostGame(Player player) {
        server = new DiscreteServer();
        try {
            server.start();
            joinGame(player, "localhost");
            hosting = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopGame() {
        if (!hosting) return;
        try {
            server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isHosting() { return hosting; }

    public boolean joinGame(Player player, String hostIP) {
        try {
            clientManager.connect(player, hostIP);
            hosting = false;
            return true;
        } catch (IOException e) {
            String msg = e.getMessage() + " | ";
            for (StackTraceElement s : e.getStackTrace()) {
                msg += s.toString() + " \n";
            }
            JOptionPane.showMessageDialog(null, e.getMessage() + " | " + msg);
            return false;
        }
    }

    public ClientManager getClientManager() { return clientManager; }

    public GameScreen getGame() {
        if (getScreen() instanceof GameScreen) return (GameScreen) getScreen();
        return null;
    }

    public void startTurn() {
        if (getGame() == null) return;

        GameScreen game = getGame();

        if (game.getMap().hasTiles(player)) {
            game.setState(GameScreen.State.PLAY);
            game.getMap().collectRevenues();
            PacketUtil.createStatChange(player, 1, 0, 0,0,0 ,0);
        }
        else game.setState(GameScreen.State.CLAIM);

        isTurn = true;
    }
    public void endTurn() {
        if (getGame() == null) return;

        PacketEndTurn packet = new PacketEndTurn();
        packet.turnPlayerID = player.getID();

        getClientManager().sendPacketTCP(packet);
        getGame().setState(GameScreen.State.WAIT);

        isTurn = false;
    }
}
