package me.jamboxman5.natac.player.ai;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.structures.constructed.ArmyOutpost;
import me.jamboxman5.natac.entity.structures.constructed.Capital;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.entity.units.Unit;
import me.jamboxman5.natac.entity.units.army.Soldier;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.*;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.sfx.Sounds;

import java.util.List;

public class BotPlayer extends Player {

    int delay;
    float delayRandomness;

    private boolean deployed = false;
    private boolean claimed = false;
    private boolean constructed = false;
    private Vector2 capitalTile = null;

    public BotPlayer() {}

    public BotPlayer(int delay, float delayRandomness) {
        super("Player_" + (int) (Math.random() * 999), getRandomClass(), Color.RED);
        this.delay = delay;
        this.delayRandomness = delayRandomness;
    }

    public void initiateMove(DiscreteServer server) {
        Map m = Natac.instance.getGame().getMap();

        new Thread(() -> {

            try {

                //BASE CASE
                if (!m.ownsTiles(this)) {
                    Thread.sleep(getDelay() * 2);
                    claimBaseTile(server, m);
                    server.botEndTurn((PacketEndTurn) endTurn());
                    return;
                }

                collectRevenues(server, m);

                if (!deployed) {
                    Tile cap = m.findTile(capitalTile);
                    List<Tile> neighbors = cap.getNeighbors();

                    for (Unit u : cap.getUnits()) {
                        u.deploy(neighbors.get((int) (Math.random() * neighbors.size())));
                    }
                    deployed = true;
                    Thread.sleep(getDelay());
                    server.botEndTurn((PacketEndTurn) endTurn());
                    System.out.println("DEPLOYED");
                } else if (!constructed) {
                    List<Tile> occupied = m.findOccupiedTiles(this);
                    for (Tile t : occupied) {
                        if (t.getTilePosition().epsilonEquals(capitalTile)) continue;
                        Structure toBuy = new ArmyOutpost(playerClass, t.getTilePosition(), Tile.getRandomPosition());
                        if (toBuy.getGoldCost() > gold || toBuy.getResourceCost() > resources) continue;
                        server.getServer().sendToAllTCP(buildStructure(t, toBuy, true));
                        server.getServer().sendToAllTCP(modifyStats(0, 0, 0, 0, -toBuy.getGoldCost(), -toBuy.getResourceCost()));
                    }
                    constructed = true;
                    server.botEndTurn((PacketEndTurn) endTurn());
                    System.out.println("CONSTRUCTED");
                } else {

                    //DO NOTHING
                    Thread.sleep(getDelay() * 2);
                    server.botEndTurn((PacketEndTurn) endTurn());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }).start();



    }

    private long getDelay() {
        if (delayRandomness > 0) {
            double random = (delay * delayRandomness * Math.random());
            if (Math.random() > .5) random *= -1;
            return (long) (delay + random);
        } else {
            return delay;
        }
    }

    private static PlayerClass getRandomClass() {
        PlayerClass[] classes = PlayerClass.values();
        return classes[(int) (Math.random() * classes.length)];
    }

    private void claimBaseTile(DiscreteServer server, Map m) {
        List<Tile> base = m.getBaseTiles();
        Tile claiming = base.get((int) (Math.random() * base.size()));

        capitalTile = claiming.getTilePosition();

        server.getServer().sendToAllTCP(claimTile(claiming));
        server.getServer().sendToAllTCP(buildStructure(claiming, new Capital(getPlayerClass(), claiming.getTilePosition()), true));
        server.getServer().sendToAllTCP(spawnUnit(claiming, new Soldier(claiming.getTilePosition(), new Vector2(-20, -20), getID())));
        server.getServer().sendToAllTCP(spawnUnit(claiming, new Soldier(claiming.getTilePosition(), new Vector2(20, -20), getID())));
    }

    private Packet modifyStats(int diffResearch,
                               int diffStatus,
                               int diffAttack,
                               int diffDefense,

                               int diffGold,
                               int diffResources) {

        PacketPlayerModify packet = new PacketPlayerModify();

        packet.sendPlayerID = Natac.instance.player.getID();
        packet.modPlayerID = getID();

        packet.diffAttack = diffAttack;
        packet.diffStatus = diffStatus;
        packet.diffResources = diffResources;
        packet.diffGold = diffGold;
        packet.diffResearch = diffResearch;
        packet.diffDefense = diffDefense;

        return packet;

    }

    private void collectRevenues(DiscreteServer server, Map map) {
        int resourcesCollected = 0;
        int goldCollected = 0;
        for (Tile t : map.getTiles(this)) {
            for (Structure s : t.getStructures()) {
                resourcesCollected += s.getResourcesPerTurn();
                goldCollected += s.getRevenuePerTurn();
            }
        }
        server.getServer().sendToAllTCP(modifyStats(0, 0, 0, 0, goldCollected, resourcesCollected));
        resources += resourcesCollected;
        gold += goldCollected;
    }

    private Packet endTurn() {
        PacketEndTurn packet = new PacketEndTurn();
        packet.turnPlayerID = getID();
        return packet;
    }

    private Packet buildStructure(Tile tile, Structure building, boolean clearObstacles) {
        PacketBuildStructure packet = new PacketBuildStructure();
        packet.builderID = getID();
        packet.tilePos = tile.getTilePosition();
        packet.structure = building;
        packet.clearObstacles = clearObstacles;
        return packet;
    }

    private Packet spawnUnit(Tile tile, Mob spawning) {
        PacketSpawnMob packet = new PacketSpawnMob();
        packet.tilePos = tile.getTilePosition();
        packet.mob = spawning;
        return packet;
    }

    private Packet claimTile(Tile t) {
        PacketClaimTile packet = new PacketClaimTile();
        packet.claimingID = getID();
        packet.tilePos = t.getTilePosition();
        return packet;
    }

}
