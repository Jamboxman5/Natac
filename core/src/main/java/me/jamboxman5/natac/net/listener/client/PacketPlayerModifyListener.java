package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketPlayerModify;
import me.jamboxman5.natac.player.Player;

public class PacketPlayerModifyListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketPlayerModify) {
            PacketPlayerModify packet = (PacketPlayerModify) obj;

            for (Player mod : Natac.instance.getClientManager().getConnectedPlayers()) {
                if (mod.getID().equals(packet.modPlayerID)) {
                    mod.setAttack(mod.getAttack() + packet.diffAttack);
                    mod.setDefense(mod.getDefense() + packet.diffDefense);
                    mod.setResearch(mod.getResearch() + packet.diffResearch);
                    mod.setStatus(mod.getStatus() + packet.diffStatus);
                    mod.setGold(mod.getGold() + packet.diffGold);
                    mod.setResources(mod.getResources() + packet.diffResources);

                }
            }
        }
    }
}
