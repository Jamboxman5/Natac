package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketSpawnMob;

public class PacketSpawnUnitListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketSpawnMob) {
            PacketSpawnMob packet = (PacketSpawnMob) obj;
            Natac.instance.getGame().getMap().findTile(packet.tilePos).add(packet.mob, false);
        }
    }
}
