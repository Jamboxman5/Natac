package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class ViciousRumorsCard extends Card {
    //Card that increases status

    //Instance variables (stats/relevant data)
    int StatusDrop = 1;

    public ViciousRumorsCard() {
        super("Vicious Rumors");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card
        //Increase the player's status value
//        Player p = Natac.instance.getClientManager().selectFromConnectedPlayers();
        PacketUtil.createStatChange(player,0,StatusDrop,0,0,0,0);
    }
}
