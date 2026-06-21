package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketDamageEntity;

public class PacketDamageEntityListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketDamageEntity) {
            PacketDamageEntity packet = (PacketDamageEntity) obj;
            Map m = Natac.instance.getGame().getMap();
            m.findEntity(packet.entity.getID()).damage(packet.healthDiff, packet.displacement);
        }
    }
}
