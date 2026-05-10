package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.player.Player;

public interface Card {

    void playCard(Player playing, Map currentMap);

}
