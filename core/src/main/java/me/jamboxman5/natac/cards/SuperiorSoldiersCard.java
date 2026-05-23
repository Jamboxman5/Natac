package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class SuperiorSoldiersCard implements Card {
    //Card that increases attack

    //Instance variables (stats/relevant data)
    int AttkBoost = 1;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's attack value by 'exampleValue'
        player.setAttack(player.getAttack() + AttkBoost);
    }
}
