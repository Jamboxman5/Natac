package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketDisconnect;
import me.jamboxman5.natac.net.packet.PacketLogin;

public class ServerDisconnectListener implements Listener {

    DiscreteServer server;

    public ServerDisconnectListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void disconnected(Connection connection) {

        PacketLogin login = server.connections.get(connection);

        PacketDisconnect packet = new PacketDisconnect();
        packet.uuid = login.connectingPlayer.getID();

        server.getServer().sendToAllExceptTCP(connection.getID(), packet);

        server.log("Player " + login.connectingPlayer.getUsername() + " disconnected.");

    }

}
