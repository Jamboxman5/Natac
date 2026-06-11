package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketBuildStructure;
import me.jamboxman5.natac.net.packet.PacketMoveUnit;

public class ServerMoveUnitListener implements Listener {

    DiscreteServer server;

    public ServerMoveUnitListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketMoveUnit) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketMoveUnit packet = (PacketMoveUnit) obj;

            server.log("Unit " + packet.unit + " (" + server.findPlayer(packet.unit.getOwner()) + ") moved from " + packet.tilePosFrom + " to " + packet.tilePosTo + " at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
