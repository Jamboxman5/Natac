package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class AdoredbyAllCard implements Card {
    //Card that increases status

    //Instance variables (stats/relevant data)
    int StatusBoost = 1;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's status value
        Card.generateStatChangePacket(player,0,StatusBoost,0,0,0,0);
    }
}

