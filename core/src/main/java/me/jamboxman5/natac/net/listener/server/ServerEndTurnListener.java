package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketEndTurn;
import me.jamboxman5.natac.net.packet.PacketStartGame;
import me.jamboxman5.natac.net.packet.PacketStartTurn;

import java.util.UUID;

public class ServerEndTurnListener implements Listener {

    DiscreteServer server;

    public ServerEndTurnListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketEndTurn) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketEndTurn packet = (PacketEndTurn) obj;

            server.log("Player " + server.usernames.get(UUID.fromString(packet.turnPlayerID)) + " (" + packet.turnPlayerID + ") ended their turn at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

            PacketStartTurn turnPacket = new PacketStartTurn();
            turnPacket.turnPlayerID = server.popPlayer().toString();
            server.getServer().sendToAllTCP(turnPacket);

        }
    }
}
