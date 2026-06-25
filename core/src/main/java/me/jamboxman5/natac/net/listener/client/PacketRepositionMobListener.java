package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketRepositionMob;

public class PacketRepositionMobListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketRepositionMob) {
            PacketRepositionMob packet = (PacketRepositionMob) obj;
            Map m = Natac.instance.getGame().getMap();
            Mob moving = m.findUnit(packet.mob.getID());
            if (moving != null) moving.setPosition(packet.newPosition);
        }
    }
}
