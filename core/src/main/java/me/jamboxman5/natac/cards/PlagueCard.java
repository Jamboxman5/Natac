package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class PlagueCard implements Card {
    //Card that increases status

    //Instance variables (stats/relevant data)
    int DefenseDrop = -1;

    @Override
    public void playCard(Player player, Map currentMap) {
        for (Player p : Natac.instance.getClientManager().getConnectedPlayers()) {
            if (!p.getID().equals(player.getID())){
                PacketUtil.createStatChange(p, 0,0,0,DefenseDrop,0,0);
            }
        }
        PacketUtil.createStatChange(player,0,2,0,0,0,0);
    }
}
