package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketBuildStructure;
import me.jamboxman5.natac.net.packet.PacketSpawnUnit;

public class ServerSpawnUnitListener implements Listener {

    DiscreteServer server;

    public ServerSpawnUnitListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketSpawnUnit) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketSpawnUnit packet = (PacketSpawnUnit) obj;

            server.log("Player " + server.findPlayer(packet.unit.getOwner()) + " (" + packet.unit.getOwner() + ") spawned a unit on the tile located at " + packet.tilePos + " at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
