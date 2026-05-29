package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketBuildStructure;
import me.jamboxman5.natac.net.packet.PacketEndTurn;
import me.jamboxman5.natac.net.packet.PacketStartTurn;

public class ServerBuildStructureListener implements Listener {

    DiscreteServer server;

    public ServerBuildStructureListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketBuildStructure) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketBuildStructure packet = (PacketBuildStructure) obj;

            server.log("Player " + server.findPlayer(packet.builderID) + " (" + packet.builderID + ") placed a structure on the tile located at " + packet.tilePos + " at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
