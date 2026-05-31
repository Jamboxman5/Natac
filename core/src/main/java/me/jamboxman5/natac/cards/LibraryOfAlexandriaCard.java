package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class LibraryOfAlexandriaCard implements Card {
    //Card that increases research

    //Instance variables (stats/relevant data)
    int ResearchBoost = 1;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's attack value by 'exampleValue'
        PacketUtil.createStatChange(player,ResearchBoost,0,0,0,0,0);
    }
}
