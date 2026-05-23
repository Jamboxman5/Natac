package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public class MechaMolesCard implements Card {
    //Mole Men specific Research Card

    //Instance variables (stats/relevant data)
    int AttackBoost = 5;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's attack value
        player.setStatus(player.getStatus() + AttackBoost);
    }
}
