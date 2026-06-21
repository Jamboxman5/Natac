package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketDamageEntity;

public class ServerDamageEntityListener implements Listener {

    DiscreteServer server;

    public ServerDamageEntityListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketDamageEntity) {
            if (server.getState() != DiscreteServer.GameState.INGAME) return;
            PacketDamageEntity packet = (PacketDamageEntity) obj;
            server.getServer().sendToAllTCP(packet);
        }
    }
}
