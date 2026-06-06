package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class AcademiaCard extends Card {
    //Card that increases status

    //Instance variables (stats/relevant data)
    int StatBoost = 2;

    public AcademiaCard() {
        super("Academia");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's status and research value
        PacketUtil.createStatChange(player,StatBoost,StatBoost,0,0,0,0);
    }
}
