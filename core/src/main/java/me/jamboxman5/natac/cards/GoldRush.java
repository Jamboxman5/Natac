package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.player.Player;

public class GoldRush implements Card {
    //This is an example card, nothing it does is important

    //Instance variables (stats/relevant data)
    int exampleValue = 3;

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's attack value by 'exampleValue'
        player.setGold(player.getGold() + exampleValue);
    }
}
