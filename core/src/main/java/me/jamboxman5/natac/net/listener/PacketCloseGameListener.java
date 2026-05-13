package me.jamboxman5.natac.net.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.packet.PacketCloseGame;
import me.jamboxman5.natac.net.packet.PacketLoginRejected;

public class PacketCloseGameListener implements Listener {

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketCloseGame) {
            PacketCloseGame close = (PacketCloseGame) obj;
            System.out.println(close.username + " has closed the server: " + close.message);
      }
    }
}
