package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class CorruptPoliticsCard extends Card {
    //Golden Keep specific Research Card

    //Instance variables (stats/relevant data)
    int StatDrop = -2;

    public CorruptPoliticsCard() {
        super("Corrupt Politics");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card
        for (Player p : Natac.instance.getClientManager().getConnectedPlayers()) {
            if (!p.getID().equals(player.getID())){
                PacketUtil.createStatChange(p, 0,StatDrop,0,StatDrop,0,0);
            }
        }

    }
}
