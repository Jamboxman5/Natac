package me.jamboxman5.natac.net;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryonet.Client;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileState;
import me.jamboxman5.natac.map.tile.TileType;
import me.jamboxman5.natac.net.listener.client.*;
import me.jamboxman5.natac.net.listener.server.ServerCloseGameListener;
import me.jamboxman5.natac.net.listener.server.ServerDisconnectListener;
import me.jamboxman5.natac.net.listener.server.ServerLoginListener;
import me.jamboxman5.natac.net.listener.server.ServerStartGameListener;
import me.jamboxman5.natac.net.packet.*;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.structures.generated.Ruins;
import me.jamboxman5.natac.units.Unit;
import me.jamboxman5.natac.units.army.Soldier;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

public class NetUtil {

    public static void registerPackets(Kryo kryo) {
        kryo.register(Packet.class);
        kryo.register(PacketLogin.class);
        kryo.register(PacketStartGame.class);
        kryo.register(PacketDisconnect.class);
        kryo.register(PacketLoginRejected.class);
        kryo.register(PacketCloseGame.class);
        kryo.register(PacketStartGame.class);

        kryo.register(Map.class);
        kryo.register(Tile.class);
        kryo.register(TileType.class);
        kryo.register(Vector.class);
        kryo.register(Vector2.class);
        kryo.register(ArrayList.class);
        kryo.register(Structure.class);
        kryo.register(Ruins.class);
        kryo.register(Unit.class);
        kryo.register(Soldier.class);
        kryo.register(Tile.Hexagon.class);
        kryo.register(TileState.class);
        kryo.register(Polygon.class);
        kryo.register(UUID.class, new DefaultSerializers.UUIDSerializer());
        kryo.register(float[].class);

    }

    public static void registerListeners(Client client) {
        client.addListener(new PacketDisconnectListener());
        client.addListener(new PacketStartGameListener());
        client.addListener(new PacketLoginListener());
        client.addListener(new PacketLoginRejectedListener());
        client.addListener(new PacketCloseGameListener());
    }

    public static void registerListeners(DiscreteServer server) {
        server.getServer().addListener(new ServerCloseGameListener(server));
        server.getServer().addListener(new ServerLoginListener(server));
        server.getServer().addListener(new ServerDisconnectListener(server));
        server.getServer().addListener(new ServerStartGameListener(server));
    }

}
