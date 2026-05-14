package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketCloseGame;

public class ServerCloseGameListener implements Listener {

    DiscreteServer server;

    public ServerCloseGameListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketCloseGame) {
            PacketCloseGame close = (PacketCloseGame) obj;
            server.getServer().sendToAllExceptTCP(conn.getID(), close);
        }
    }
}
