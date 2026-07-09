package me.jamboxman5.natac.player.ai;

import com.badlogic.gdx.graphics.Color;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.Packet;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketEndTurn;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;

import java.util.List;

public class BotPlayer extends Player {

    int delay;
    float delayRandomness;
    boolean useRandom;

    public BotPlayer() {}

    public BotPlayer(int delay, float delayRandomness, boolean useRandom) {
        super("Player_" + (int) (Math.random() * 999), getRandomClass(), Color.RED);
        this.delay = delay;
        this.delayRandomness = delayRandomness;
        this.useRandom = useRandom;
    }

    public void initiateMove(DiscreteServer server) {
        Map m = Natac.instance.getGame().getMap();

        new Thread(() -> {

            try {

                //BASE CASE
                if (!m.ownsTiles(this)) {
                    Thread.sleep(getDelay() * 2);
                    server.getServer().sendToAllTCP(claimBaseTile(m));
                    Thread.sleep(getDelay());
                    server.botEndTurn((PacketEndTurn) endTurn());

                } else {

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }).start();



    }

    private long getDelay() {
        if (useRandom) {
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

    private Packet claimBaseTile(Map m) {
        List<Tile> base = m.getBaseTiles();
        Tile claiming = base.get((int) (Math.random() * base.size()));

        PacketClaimTile packet = new PacketClaimTile();
        packet.claimingID = getID();
        packet.tilePos = claiming.getTilePosition();
        return packet;

    }

    private Packet endTurn() {
        PacketEndTurn packet = new PacketEndTurn();
        packet.turnPlayerID = getID();
        return packet;
    }

}
