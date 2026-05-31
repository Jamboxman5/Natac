package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class OfferingPlateCard implements Card {
    //Holy Empire Research Card

    //Instance variables (stats/relevant data)
    int GoldChange = 1;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card
        for (Player p : Natac.instance.getClientManager().getConnectedPlayers()) {
            if (!p.getID().equals(player.getID())){
                PacketUtil.createStatChange(p, 0,0,0,0,-GoldChange,0);
            }
        }
        //Player gains 5 gold and decreases the gold of the other players by 1
        PacketUtil.createStatChange(player,0,0,0,0,5,0);
    }
}
