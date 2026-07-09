package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketLogin;
import me.jamboxman5.natac.net.packet.PacketLoginRejected;
import me.jamboxman5.natac.net.packet.PacketStartGame;
import me.jamboxman5.natac.net.packet.PacketStartTurn;

public class ServerStartGameListener implements Listener {

    DiscreteServer server;

    public ServerStartGameListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketStartGame) {
            if (server.getState() != DiscreteServer.GameState.LOBBY) return;

            PacketStartGame packet = (PacketStartGame) obj;

            server.log("Game started at " + packet.timestamp + ": " + packet.map.toString());
            server.getServer().sendToAllTCP(packet);
            server.setState(DiscreteServer.GameState.INGAME);

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    PacketStartTurn turnPacket = new PacketStartTurn();
                    turnPacket.turnPlayerID = server.popPlayer().getID();
                    server.getServer().sendToAllTCP(turnPacket);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            server.populateBots();

        }
    }
}
