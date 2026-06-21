package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketSpawnMob;

public class ServerSpawnUnitListener implements Listener {

    DiscreteServer server;

    public ServerSpawnUnitListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketSpawnMob) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketSpawnMob packet = (PacketSpawnMob) obj;

            server.log("Player " + server.findPlayer(packet.mob.getOwner()) + " (" + packet.mob.getOwner() + ") spawned a mob on the tile located at " + packet.tilePos + " at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
