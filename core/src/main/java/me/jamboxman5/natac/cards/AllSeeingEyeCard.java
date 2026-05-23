package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class AllSeeingEyeCard implements Card {
    //Mole Men specific Research Card

    //Instance variables (stats/relevant data)
    int StatBoost = 2;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's defense and research
        player.setDefense(player.getDefense() + StatBoost);
        player.setResearch(player.getResearch() + StatBoost);
    }
}
