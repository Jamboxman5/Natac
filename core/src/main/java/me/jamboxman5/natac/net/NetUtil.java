package me.jamboxman5.natac.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import me.jamboxman5.natac.net.listener.client.*;
import me.jamboxman5.natac.net.listener.server.ServerCloseGameListener;
import me.jamboxman5.natac.net.listener.server.ServerDisconnectListener;
import me.jamboxman5.natac.net.listener.server.ServerLoginListener;
import me.jamboxman5.natac.net.packet.*;

public class NetUtil {

    public static void registerPackets(Kryo kryo) {
        kryo.register(Packet.class);
        kryo.register(PacketLogin.class);
        kryo.register(PacketMove.class);
        kryo.register(PacketDisconnect.class);
        kryo.register(PacketLoginRejected.class);
        kryo.register(PacketCloseGame.class);
    }

    public static void registerListeners(Client client) {
        client.addListener(new PacketDisconnectListener());
        client.addListener(new PacketMoveListener());
        client.addListener(new PacketLoginListener());
        client.addListener(new PacketLoginRejectedListener());
        client.addListener(new PacketCloseGameListener());
    }

    public static void registerListeners(DiscreteServer server) {
        server.getServer().addListener(new ServerCloseGameListener(server));
        server.getServer().addListener(new ServerLoginListener(server));
        server.getServer().addListener(new ServerDisconnectListener(server));
    }

}
