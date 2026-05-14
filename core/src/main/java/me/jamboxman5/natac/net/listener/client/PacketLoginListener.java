package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketLogin;

public class PacketLoginListener implements Listener {

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketLogin) {
            Natac.instance.getClientManager().connectPlayer((PacketLogin) obj);
      }
    }
}
