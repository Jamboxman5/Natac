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

    public BotPlayer() {
        super("Player_" + (int) (Math.random() * 999), getRandomClass(), Color.RED);
    }

    public void initiateMove(DiscreteServer server) {
        Map m = Natac.instance.getGame().getMap();

        if (!m.ownsTiles(this)) {
            server.getServer().sendToAllTCP(claimBaseTile(m));
            server.botEndTurn((PacketEndTurn) endTurn());
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
