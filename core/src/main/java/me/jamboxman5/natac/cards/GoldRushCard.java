package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class GoldRushCard implements Card {
    //Card that gives player gold

    //Instance variables (stats/relevant data)
    int GoldBoost = 3;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's gold
        player.setGold(player.getGold() + GoldBoost);
    }
}
