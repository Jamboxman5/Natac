package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.player.Player;

public class Pacifism implements Card {
    //Card reduces attack value to 0 then boosts defense by 3 + original attack

    //Instance variables (stats/relevant data)

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        int originalAttack = player.getAttack();
        player.setAttack(player.getAttack() - originalAttack);
        player.setDefense(player.getDefense() + (3 + originalAttack));
    }
}
