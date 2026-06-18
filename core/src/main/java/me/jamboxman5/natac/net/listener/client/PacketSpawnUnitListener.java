package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketBuildStructure;
import me.jamboxman5.natac.net.packet.PacketSpawnUnit;
import me.jamboxman5.natac.sfx.Sounds;

public class PacketSpawnUnitListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketSpawnUnit) {
            PacketSpawnUnit packet = (PacketSpawnUnit) obj;
            Natac.instance.getGame().getMap().findTile(packet.tilePos).addUnit(packet.unit);
        }
    }
}
