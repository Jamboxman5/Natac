package me.jamboxman5.natac.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import me.jamboxman5.natac.net.packet.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class DiscreteServer {

    private Server server;

    public HashMap<Connection, PacketLogin> connections;
    public HashMap<UUID, String> usernames;

    public enum GameState {
        LOBBY, INGAME
    }

    private GameState state;

    public DiscreteServer() {
        server = new Server(65536, 65536);
    }

    public void start() throws IOException {
        Kryo kryo = server.getKryo();

        state = GameState.LOBBY;

        connections = new HashMap<>();
        usernames = new HashMap<>();

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

    public void addUsername(String uuid, String username) {
        usernames.put(UUID.fromString(uuid), username);
    }

    public void removeUsername(String uuid) {
        usernames.remove(UUID.fromString(uuid));
    }

    public String getUsername(String uuid) {
        return usernames.get(UUID.fromString(uuid));
    }

    public void log(String log) {
        System.out.println("\u001B[33mSERVER::  " + log + "\u001B[0m");
    }

    public void logSevere(String log) {
        System.out.println("\u001B[31mSERVER WARNING::  " + log + "\u001B[0m");
    }

}
