package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketRepositionMob;

public class ServerRepositionUnitListener implements Listener {

    DiscreteServer server;

    public ServerRepositionUnitListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketRepositionMob) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketRepositionMob packet = (PacketRepositionMob) obj;
            server.getServer().sendToAllExceptUDP(conn.getID(), packet);
        }
    }
}
