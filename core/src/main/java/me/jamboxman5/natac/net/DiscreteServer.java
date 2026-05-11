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

    HashMap<Connection, PacketLogin> connections;

    private enum GameState {
        LOBBY, INGAME
    }

    private GameState state;

    public DiscreteServer() {
        server = new Server();
    }

    public void start() throws IOException {
        Kryo kryo = server.getKryo();

        connections = new HashMap<Connection, PacketLogin>();

        NetUtil.registerPackets(kryo);

        server.addListener(new Listener() {
            public void received(Connection conn, Object obj) {
                if (obj instanceof PacketLogin) {
                    if (state != GameState.LOBBY) {
                        PacketLoginRejected rejection = new PacketLoginRejected();
                        rejection.message = "The game has already started!";
                        rejection.timestamp = System.currentTimeMillis();
                        conn.sendTCP(rejection);
                        conn.close();
                        return;
                    }
                    PacketLogin login = (PacketLogin) obj;
                    connections.put(conn, login);
                    System.out.println("User connected: " + login.username + " (" + login.uuid + ")");
                    server.sendToAllExceptTCP(conn.getID(), login);
                    for (Connection c : getServer().getConnections()) {
                        if (c.getID() != conn.getID()) {
                            server.sendToTCP(conn.getID(), connections.get(c));
                        }
                    }

                }
                if (obj instanceof PacketMove) {
                    PacketMove move = (PacketMove) obj;
                    server.sendToAllExceptUDP(conn.getID(), move);
                }
            }
        });

        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection connection) {

                PacketLogin login = connections.get(connection);

                PacketDisconnect packet = new PacketDisconnect();
                packet.uuid = login.uuid;
                packet.username = login.username;

                server.sendToAllExceptTCP(connection.getID(), packet);

                System.out.println("Player " + login.username + " disconnected.");
            }
        });

        server.bind(13331, 13331);
        server.start();
        System.out.println("SERVER STARTED!");
    }

    public void stop() throws IOException {
        server.stop();
        server.dispose();
    }

    public Server getServer() { return server; }

}
