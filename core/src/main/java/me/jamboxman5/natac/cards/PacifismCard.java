package me.jamboxman5.natac.cards;

import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketUtil;
import me.jamboxman5.natac.player.Player;

public class PacifismCard extends Card {
    //Card reduces attack value to 0 then boosts defense by 3 + original attack

    //Instance variables (stats/relevant data)

    public PacifismCard() {
        super("Pacifism");
    }

    @Override
    public void playCard(Player player, Map currentMap) {
        //Define effects to happen when card is played
        //Player variable is the instance of the player who is playing this card

        int originalAttack = player.getAttack();
        PacketUtil.createStatChange(player,0,0,(-originalAttack),(3+originalAttack),0,0);
    }
}
