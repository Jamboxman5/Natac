package me.jamboxman5.natac.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import me.jamboxman5.natac.net.listener.*;
import me.jamboxman5.natac.net.packet.*;

public class NetUtil {

    public static void registerPackets(Kryo kryo) {
        kryo.register(Packet.class);
        kryo.register(PacketLogin.class);
        kryo.register(PacketMove.class);
        kryo.register(PacketDisconnect.class);
    }

    public static void registerListeners(Client client) {
        client.addListener(new PacketDisconnectListener());
        client.addListener(new PacketMoveListener());
        client.addListener(new PacketLoginListener());
    }

}
