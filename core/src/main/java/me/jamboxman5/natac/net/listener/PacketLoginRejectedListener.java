package me.jamboxman5.natac.net.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.packet.PacketLogin;
import me.jamboxman5.natac.net.packet.PacketLoginRejected;

public class PacketLoginRejectedListener implements Listener {

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketLoginRejected) {
            PacketLoginRejected login = (PacketLoginRejected) obj;
            System.out.println("Login Rejected: " + login.message);
      }
    }
}
