package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketMoveUnit;
import me.jamboxman5.natac.net.packet.PacketRepositionUnit;

public class ServerRepositionUnitListener implements Listener {

    DiscreteServer server;

    public ServerRepositionUnitListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketRepositionUnit) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketRepositionUnit packet = (PacketRepositionUnit) obj;
            server.getServer().sendToAllUDP(packet);
        }
    }
}
