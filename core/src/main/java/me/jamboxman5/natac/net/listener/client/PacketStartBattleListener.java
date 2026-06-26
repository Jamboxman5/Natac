package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketStartBattle;
import me.jamboxman5.natac.screen.GameScreen;

public class PacketStartBattleListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketStartBattle) {
            PacketStartBattle packet = (PacketStartBattle) obj;
            if (packet.fightingPlayers.contains(Natac.instance.player.getID())) {
                GameScreen game = Natac.instance.getGame();
                game.startBattle(game.getMap().findTile(packet.tilePos));
            }
        }
    }
}
