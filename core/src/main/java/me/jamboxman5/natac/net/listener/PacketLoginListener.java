package me.jamboxman5.natac.net.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.packet.PacketLogin;

public class PacketLoginListener implements Listener {

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketLogin) {
            PacketLogin login = (PacketLogin) obj;
      }
    }
}
