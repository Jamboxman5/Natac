package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class FoolsGoldCard extends Card {
    //Card that increases status

    //Instance variables (stats/relevant data)
    int GoldDrop = -3;

    public FoolsGoldCard() {
        super("Fools' Gold");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card
        //Decrease the gold stat of another player by 3
        Player p = Natac.instance.getClientManager().selectFromConnectedPlayers();
        PacketUtil.createStatChange(p,0,0,0,0,GoldDrop,0);
    }
}
