package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class CityGuardCard implements Card {
    //Card that increases defense

    //Instance variables (stats/relevant data)
    int DefBoost = 1;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's defense value
        player.setDefense(player.getDefense() + DefBoost);
    }
}
