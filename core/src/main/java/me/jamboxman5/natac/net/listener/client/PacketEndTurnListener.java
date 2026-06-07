package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketEndTurn;
import me.jamboxman5.natac.net.packet.PacketStartTurn;

public class PacketEndTurnListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketEndTurn) {
            Natac.instance.updateAfterTurn();
        }
    }
}
