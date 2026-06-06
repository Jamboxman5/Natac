package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class BountifulHarvestCard extends Card {
    //Card that boosts resources

    //Instance variables (stats/relevant data)
    int ResourceBoost = 10;

    public BountifulHarvestCard() {
        super("Bountiful Harvest");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        //Increase the player's resources
        PacketUtil.createStatChange(player,0,0,0,0,0,ResourceBoost);
    }
}
