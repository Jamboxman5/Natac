package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketLogin;
import me.jamboxman5.natac.net.packet.PacketLoginRejected;

import java.util.UUID;

public class ServerLoginListener implements Listener {

    DiscreteServer server;

    public ServerLoginListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {

        if (obj instanceof PacketLogin) {

            if (server.getState() != DiscreteServer.GameState.LOBBY) {
                PacketLoginRejected rejection = new PacketLoginRejected();
                rejection.message = "The game has already started!";
                rejection.timestamp = System.currentTimeMillis();
                conn.sendTCP(rejection);
                conn.close();
                return;
            }

            PacketLogin login = (PacketLogin) obj;

            server.connections.put(conn, login);
            server.addUsername(login.uuid, login.username);
            server.playerTurnQueue.addLast(UUID.fromString(login.uuid));

            server.log("User connected: " + login.username + " (" + login.uuid + ")");
            server.getServer().sendToAllExceptTCP(conn.getID(), login);

            for (Connection c : server.getServer().getConnections()) {
                if (c.getID() != conn.getID()) {
                    server.getServer().sendToTCP(conn.getID(), server.connections.get(c));
                }
            }

        }
    }
}
