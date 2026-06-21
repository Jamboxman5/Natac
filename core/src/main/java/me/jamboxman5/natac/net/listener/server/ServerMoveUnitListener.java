package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketMoveMob;

public class ServerMoveUnitListener implements Listener {

    DiscreteServer server;

    public ServerMoveUnitListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketMoveMob) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketMoveMob packet = (PacketMoveMob) obj;

            server.log("Mob " + packet.mob + " (" + server.findPlayer(packet.mob.getOwner()) + ") moved from " + packet.tilePosFrom + " to " + packet.tilePosTo + " at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
