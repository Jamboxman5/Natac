package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.units.Unit;

import java.util.HashSet;
import java.util.UUID;

public class PacketUtil {

    public static void buildStructure(Structure s, Vector2 tilePos) {
        PacketBuildStructure packet = new PacketBuildStructure();
        packet.builderID = Natac.instance.player.getID();
        packet.tilePos = tilePos;
        packet.structure = s;
        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void spawnUnit(Mob spawning, Vector2 tilePos) {
        PacketSpawnMob packet = new PacketSpawnMob();
        packet.tilePos = tilePos;
        packet.mob = spawning;
        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void moveMob(Mob moving, Vector2 tileTo, Vector2 tileFrom) {
        PacketMoveMob packet = new PacketMoveMob();
        packet.mob = moving;
        packet.tilePosTo = tileTo;
        packet.tilePosFrom = tileFrom   ;
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

    public static void repositionMob(Mob repositioning, Vector2 newPosition) {
        if (!isOwner(repositioning)) return;
        PacketRepositionMob packet = new PacketRepositionMob();
        packet.mob = repositioning;
        packet.newPosition = newPosition;

        Natac.instance.getClientManager().sendPacketUDP(packet);
    }

    public static void damageEntity(Entity entity, int damage, Vector2 displacement) {
        PacketDamageEntity packet = new PacketDamageEntity();
        packet.entity = entity;
        packet.healthDiff = damage;
        packet.displacement = displacement;

        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    public static void startBattle(Vector2 tilePos, HashSet<UUID> fighters) {
        PacketStartBattle packet = new PacketStartBattle();
        packet.fightingPlayers = fighters;
        packet.tilePos = tilePos;

        Natac.instance.getClientManager().sendPacketTCP(packet);
    }

    private static boolean isOwner(Mob mob) {
        return (mob.getOwner().equals(Natac.instance.player.getID()));
    }
}
