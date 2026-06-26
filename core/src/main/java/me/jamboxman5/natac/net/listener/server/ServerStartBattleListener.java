package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketStartBattle;

import java.util.UUID;

public class ServerStartBattleListener implements Listener {

    DiscreteServer server;

    public ServerStartBattleListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketStartBattle) {

            PacketStartBattle packet = (PacketStartBattle) obj;
            StringBuilder log = new StringBuilder("Battle started at " + packet.tilePos.x + ", " + packet.tilePos.y + " by ");
            for (UUID id : packet.fightingPlayers) log.append("(").append(server.findPlayer(id)).append(")");

            server.log(log + ".");
            server.getServer().sendToAllTCP(packet);

        }
    }
}
