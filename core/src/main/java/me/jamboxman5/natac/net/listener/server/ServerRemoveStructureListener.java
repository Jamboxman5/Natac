package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketBuildStructure;
import me.jamboxman5.natac.net.packet.PacketRemoveStructure;

public class ServerRemoveStructureListener implements Listener {

    DiscreteServer server;

    public ServerRemoveStructureListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketRemoveStructure) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketRemoveStructure packet = (PacketRemoveStructure) obj;

            server.log(packet.structure.toString() + " structure on the tile located at " + packet.tilePos + " was destroyed at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
