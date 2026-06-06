package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class SuperiorSoldiersCard extends Card {
    //Card that increases attack

    //Instance variables (stats/relevant data)
    int AttkBoost = 1;

    public SuperiorSoldiersCard() {
        super("Superior Soldiers");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's attack value by 'exampleValue'
        PacketUtil.createStatChange(player,1,0,AttkBoost,0,0,0);
    }
}
