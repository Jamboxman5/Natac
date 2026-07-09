package me.jamboxman5.natac.player.ai;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.structures.Structure;
import me.jamboxman5.natac.entity.structures.constructed.Capital;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.entity.units.army.Soldier;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.*;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;

import java.util.List;

public class BotPlayer extends Player {

    int delay;
    float delayRandomness;

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
                    Thread.sleep(getDelay());
                    server.botEndTurn((PacketEndTurn) endTurn());

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

        server.getServer().sendToAllTCP(claimTile(claiming));
        server.getServer().sendToAllTCP(buildStructure(claiming, new Capital(getPlayerClass(), claiming.getTilePosition()), true));
        server.getServer().sendToAllTCP(spawnUnit(claiming, new Soldier(claiming.getTilePosition(), new Vector2(-20, -20), getID())));
        server.getServer().sendToAllTCP(spawnUnit(claiming, new Soldier(claiming.getTilePosition(), new Vector2(20, -20), getID())));
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
