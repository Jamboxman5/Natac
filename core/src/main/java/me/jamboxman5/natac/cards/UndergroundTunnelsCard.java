package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class UndergroundTunnelsCard implements Card {
    //Mole Men specific Research Card

    //Instance variables (stats/relevant data)
    int DefenseDrop = 3;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Defense drop to all others
        for (Player p : Natac.instance.getClientManager().getConnectedPlayers()) {
            if (!p.getID().equals(player.getID())){
                Card.generateStatChangePacket(p, 0,0,0,DefenseDrop,0,0);
            }
        }
    }
}
