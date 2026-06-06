package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class AllSeeingEyeCard extends Card {
    //Steel City specific Research Card

    //Instance variables (stats/relevant data)
    int StatBoost = 2;

    public AllSeeingEyeCard() {
        super("All Seeing Eye");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's defense and research
        PacketUtil.createStatChange(player,StatBoost,0,0,StatBoost,0,0);
    }
}
