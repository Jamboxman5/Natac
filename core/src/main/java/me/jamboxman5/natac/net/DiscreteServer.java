package me.jamboxman5.natac.net;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import me.jamboxman5.natac.net.packet.*;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.ai.BotPlayer;
import me.jamboxman5.natac.util.Settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class DiscreteServer {

    private Server server;

    public HashMap<Connection, PacketLogin> connections;
    public Array<Player> connectedPlayers;

    public Queue<Player> playerTurnQueue;

    private final int maxPlayers;

    public void setState(GameState gameState) { this.state = gameState; }

    public Player findPlayer(UUID id) {
        for (Player p : connectedPlayers) {
            if (p.getID().equals(id)) return p;
        }
        return null;
    }

    public enum GameState {
        LOBBY, INGAME
    }

    private GameState state;

    public DiscreteServer() {
        server = new Server(65536, 65536);
        maxPlayers = Settings.maxPlayers;
    }

    public void start() throws IOException {
        Kryo kryo = server.getKryo();

        state = GameState.LOBBY;

        connections = new HashMap<>();
        connectedPlayers = new Array<>();
        playerTurnQueue = new Queue<>();

        NetUtil.registerPackets(kryo);

        NetUtil.registerListeners(this);

        server.bind(13531, 13531);
        server.start();
        log("Server started on port 13531!");
    }

    public void stop() throws IOException {
        server.stop();
        server.dispose();
    }

    public GameState getState() { return state; }

    public Server getServer() { return server; }

    public void log(String log) {
        System.out.println("\u001B[33mSERVER::  " + log + "\u001B[0m");
    }

    public void logSevere(String log) {
        System.out.println("\u001B[31mSERVER WARNING::  " + log + "\u001B[0m");
    }

    public Player popPlayer() {
        Player player = playerTurnQueue.removeFirst();
        playerTurnQueue.addLast(player);
        return player;
    }

    public Player peekPlayer() {
        return playerTurnQueue.first();
    }

    public void populateBots(int botDelay, float randomRatio) {
        if (connectedPlayers.size < maxPlayers) {
            int toAdd = maxPlayers - connectedPlayers.size;
            for (int i = 0; i < toAdd; i++) {
                BotPlayer bot = new BotPlayer(botDelay, randomRatio);
                playerTurnQueue.addLast(bot);
                connectedPlayers.add(bot);

                log("Generated BotPlayer: " + bot.getUsername() + " (" + bot.getID() + ")");

                PacketLogin login = new PacketLogin();
                login.connectingPlayer = bot;
                server.sendToAllTCP(login);

            }
        }
    }

    public void botEndTurn(PacketEndTurn packet) {

        log("Player " + findPlayer(packet.turnPlayerID) + " (" + packet.turnPlayerID + ") ended their turn at " + packet.timestamp + ". ");
        getServer().sendToAllTCP(packet);

        Player next = popPlayer();

        PacketStartTurn turnPacket = new PacketStartTurn();
        turnPacket.turnPlayerID = next.getID();
        getServer().sendToAllTCP(turnPacket);

        if (next instanceof BotPlayer) {
            ((BotPlayer) next).initiateMove(this);
        }

    }

}
