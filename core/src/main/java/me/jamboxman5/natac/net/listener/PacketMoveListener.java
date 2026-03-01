package me.jamboxman5.natac.net.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.packet.PacketMove;

public class PacketMoveListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketMove) {
//            System.out.println("Move: " + move.uuid + " - (" + move.x + "," + move.y + ") | angle: " + move.rotation);
        }
    }
}
