package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.structures.constructed.*;
import me.jamboxman5.natac.entity.structures.generated.Ruins;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.units.Unit;

import java.util.HashSet;
import java.util.UUID;

public class PacketUtil {

    public static void buildStructure(Structure s, Vector2 tilePos, boolean clearObstacles) {
        PacketBuildStructure packet = new PacketBuildStructure();
        packet.builderID = Natac.instance.player.getID();
        packet.tilePos = tilePos;
        packet.structure = s;
        packet.clearObstacles = clearObstacles;
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

    public static void upgradeRuins(Player p, Ruins s) {
        PacketRemoveStructure packet1 = new PacketRemoveStructure();
        packet1.structure = s;
        packet1.tilePos = s.getTilePosition();
        Natac.instance.getClientManager().sendPacketTCP(packet1);

        PacketBuildStructure packet2 = new PacketBuildStructure();
        packet2.tilePos = s.getTilePosition();
        packet2.clearObstacles = true;
        packet2.builderID = p.getID();
        packet2.structure = getRandomRuinStructure(p, s);
        Natac.instance.getClientManager().sendPacketTCP(packet2);

    }

    private static Structure getRandomRuinStructure(Player p, Ruins ruins) {
        double roll = Math.random();
        if (roll < .2) {
            return new ArmyOutpost(p.getPlayerClass(), ruins.getTilePosition(), ruins.getPosition());
        } else if (roll < .4) {
            return new Logger(p.getPlayerClass(), ruins.getTilePosition(), ruins.getPosition());
        } else if (roll < .6) {
            return new Depot(p.getPlayerClass(), ruins.getTilePosition(), ruins.getPosition());
        } else if (roll < .8) {
            return new Quarry(p.getPlayerClass(), ruins.getTilePosition(), ruins.getPosition());
        } else {
            return new Library(p.getPlayerClass(), ruins.getTilePosition(), ruins.getPosition());
        }
    }
}
