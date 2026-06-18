package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.units.Unit;

public class PacketUtil {

    public static void buildStructure(Structure s, Vector2 tilePos) {
        PacketBuildStructure packet = new PacketBuildStructure();
        packet.builderID = Natac.instance.player.getID();
        packet.tilePos = tilePos;
        packet.structure = s;
        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void spawnUnit(Unit unit, Vector2 tilePos) {
        PacketSpawnUnit packet = new PacketSpawnUnit();
        packet.tilePos = tilePos;
        packet.unit = unit;
        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void moveUnit(Unit unit, Vector2 to, Vector2 from) {
        PacketMoveUnit packet = new PacketMoveUnit();
        packet.unit = unit;
        packet.tilePosTo = to;
        packet.tilePosFrom = from;
        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void createStatChange(Player target, int diffResearch,
                                        int diffStatus,
                                        int diffAttack,
                                        int diffDefense,

                                        int diffGold,
                                        int diffResources) {

        PacketPlayerModify packet = new PacketPlayerModify();

        packet.sendPlayerID = Natac.instance.player.getID();
        packet.modPlayerID = target.getID();

        packet.diffAttack = diffAttack;
        packet.diffStatus = diffStatus;
        packet.diffResources = diffResources;
        packet.diffGold = diffGold;
        packet.diffResearch = diffResearch;
        packet.diffDefense = diffDefense;

        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void repositionUnit(Unit unit, Vector2 newPosition) {
        PacketRepositionUnit packet = new PacketRepositionUnit();
        packet.unit = unit;
        packet.newPosition = newPosition;

        Natac.instance.getClientManager().sendPacketUDP(packet);
    }
}
