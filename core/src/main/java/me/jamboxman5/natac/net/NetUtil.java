package me.jamboxman5.natac.net;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.esotericsoftware.kryonet.Client;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.structures.constructed.*;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.map.tile.TileState;
import me.jamboxman5.natac.map.tile.TileType;
import me.jamboxman5.natac.net.listener.client.*;
import me.jamboxman5.natac.net.listener.server.*;
import me.jamboxman5.natac.net.packet.*;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.structures.generated.Ruins;
import me.jamboxman5.natac.entity.structures.prop.Prop;
import me.jamboxman5.natac.entity.structures.prop.Tree;
import me.jamboxman5.natac.entity.units.Unit;
import me.jamboxman5.natac.entity.units.army.Soldier;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

public class NetUtil {

    public static void registerPackets(Kryo kryo) {

        /* PACKETS */
        kryo.register(Packet.class);
        kryo.register(PacketLogin.class);
        kryo.register(PacketStartGame.class);
        kryo.register(PacketDisconnect.class);
        kryo.register(PacketLoginRejected.class);
        kryo.register(PacketCloseGame.class);
        kryo.register(PacketStartGame.class);
        kryo.register(PacketClaimTile.class);
        kryo.register(PacketStartTurn.class);
        kryo.register(PacketEndTurn.class);
        kryo.register(PacketPlayerModify.class);
        kryo.register(PacketBuildStructure.class);
        kryo.register(PacketSpawnMob.class);
        kryo.register(PacketMoveMob.class);
        kryo.register(PacketRepositionMob.class);
        kryo.register(PacketDamageEntity.class);



        /* UTILITIES */
        kryo.register(Vector.class);
        kryo.register(Vector2.class);
        kryo.register(ArrayList.class);
        kryo.register(UUID.class, new DefaultSerializers.UUIDSerializer());
        kryo.register(float[].class);



        /* MAP * WORLD */
        kryo.register(Player.class);
        kryo.register(PlayerClass.class);
        kryo.register(Map.class);
        kryo.register(Tile.class);
        kryo.register(TileState.class);
        kryo.register(TileType.class);
        kryo.register(Tile.Hexagon.class);



        /* ENTITIES */
        kryo.register(Entity.class);

        //UNITS
        kryo.register(Mob.class);
        kryo.register(Unit.class);
        kryo.register(Soldier.class);

        //STRUCTURES
        kryo.register(Structure.class);
        kryo.register(TownHall.class);
        kryo.register(Ruins.class);
        kryo.register(Barracks.class);
        kryo.register(ArmyOutpost.class);
        kryo.register(Depot.class);
        kryo.register(Library.class);
        kryo.register(Logger.class);
        kryo.register(Quarry.class);
        kryo.register(ScoutTower.class);

        //PROPS
        kryo.register(Prop.class);
        kryo.register(Tree.class);

    }

    public static void registerListeners(Client client) {
        client.addListener(new PacketDisconnectListener());
        client.addListener(new PacketStartGameListener());
        client.addListener(new PacketLoginListener());
        client.addListener(new PacketLoginRejectedListener());
        client.addListener(new PacketCloseGameListener());
        client.addListener(new PacketClaimTileListener());
        client.addListener(new PacketStartTurnListener());
        client.addListener(new PacketPlayerModifyListener());
        client.addListener(new PacketBuildStructureListener());
        client.addListener(new PacketSpawnUnitListener());
        client.addListener(new PacketEndTurnListener());
        client.addListener(new PacketMoveUnitListener());
        client.addListener(new PacketRepositionMobListener());
        client.addListener(new PacketDamageEntityListener());
    }

    public static void registerListeners(DiscreteServer server) {
        server.getServer().addListener(new ServerCloseGameListener(server));
        server.getServer().addListener(new ServerLoginListener(server));
        server.getServer().addListener(new ServerDisconnectListener(server));
        server.getServer().addListener(new ServerStartGameListener(server));
        server.getServer().addListener(new ServerClaimTileListener(server));
        server.getServer().addListener(new ServerEndTurnListener(server));
        server.getServer().addListener(new ServerPlayerModifyListener(server));
        server.getServer().addListener(new ServerBuildStructureListener(server));
        server.getServer().addListener(new ServerSpawnUnitListener(server));
        server.getServer().addListener(new ServerMoveUnitListener(server));
        server.getServer().addListener(new ServerRepositionUnitListener(server));
        server.getServer().addListener(new ServerDamageEntityListener(server));
    }

}
