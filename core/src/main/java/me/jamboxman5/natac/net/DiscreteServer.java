package me.jamboxman5.natac.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import me.jamboxman5.natac.net.packet.*;

import java.io.IOException;
import java.util.HashMap;

public class DiscreteServer {

    private Server server;

    public HashMap<Connection, PacketLogin> connections;

    public enum GameState {
        LOBBY, INGAME
    }

    private GameState state;

    public DiscreteServer() {
        server = new Server();
    }

    public void start() throws IOException {
        Kryo kryo = server.getKryo();

        state = GameState.LOBBY;

        connections = new HashMap<>();

        NetUtil.registerPackets(kryo);

        NetUtil.registerListeners(this);

        server.bind(13531, 13531);
        server.start();
        System.out.println("SERVER STARTED!");
    }

    public void stop() throws IOException {
        server.stop();
        server.dispose();
    }

    public GameState getState() { return state; }

    public Server getServer() { return server; }

}
