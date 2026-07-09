package me.jamboxman5.natac.net.listener.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketStartGame;
import me.jamboxman5.natac.net.packet.PacketStartTurn;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.sfx.Sounds;

import java.util.UUID;

public class PacketStartTurnListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketStartTurn) {
            PacketStartTurn packet = (PacketStartTurn) obj;

            Natac.instance.getClientManager().setCurrentPlayer(packet.turnPlayerID);

            if (packet.turnPlayerID.equals(Natac.instance.player.getID())) {
                Natac.instance.startTurn();
            } else {
                Sounds.END_TURN.play();
            }
        }
    }
}
