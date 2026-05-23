package me.jamboxman5.natac.net.listener.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketStartGame;
import me.jamboxman5.natac.screen.GameScreen;

import java.util.UUID;

public class PacketClaimTileListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketClaimTile) {
            PacketClaimTile packet = (PacketClaimTile) obj;
            Natac.instance.getGame().getMap().findTile(packet.tilePos).claim(packet.claimingID, false);
        }
    }
}
