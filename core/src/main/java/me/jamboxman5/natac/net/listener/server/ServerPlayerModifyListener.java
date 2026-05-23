package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketEndTurn;
import me.jamboxman5.natac.net.packet.PacketPlayerModify;
import me.jamboxman5.natac.net.packet.PacketStartTurn;
import me.jamboxman5.natac.player.Player;

public class ServerPlayerModifyListener implements Listener {

    DiscreteServer server;

    public ServerPlayerModifyListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketPlayerModify) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketPlayerModify packet = (PacketPlayerModify) obj;

            server.log("Player " + server.findPlayer(packet.sendPlayerID) + " just modified the stats of player " + server.findPlayer(packet.modPlayerID) + " at " + packet.timestamp + ". ");
            server.getServer().sendToAllTCP(packet);

            Player mod = server.findPlayer(packet.modPlayerID);
            mod.setAttack(mod.getAttack() + packet.diffAttack);
            mod.setDefense(mod.getDefense() + packet.diffDefense);
            mod.setResearch(mod.getResearch() + packet.diffResearch);
            mod.setStatus(mod.getStatus() + packet.diffStatus);
            mod.setGold(mod.getGold() + packet.diffGold);
            mod.setResources(mod.getResources() + packet.diffResources);

        }
    }
}
