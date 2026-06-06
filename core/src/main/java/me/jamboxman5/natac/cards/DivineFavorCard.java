package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class DivineFavorCard extends Card {
    //Holy Empire Research Card

    //Instance variables (stats/relevant data)
    int StatBoost = 1;
    int ResourceIncrease = 10;

    public DivineFavorCard() {
        super("Divine Favor");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the attk def and resources of the player
        PacketUtil.createStatChange(player,0,0,StatBoost,StatBoost,0,ResourceIncrease);
    }
}
