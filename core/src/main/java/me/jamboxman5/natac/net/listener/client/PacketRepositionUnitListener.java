package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketMoveUnit;
import me.jamboxman5.natac.net.packet.PacketRepositionUnit;

public class PacketRepositionUnitListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketRepositionUnit) {
            PacketRepositionUnit packet = (PacketRepositionUnit) obj;
            Map m = Natac.instance.getGame().getMap();
            m.findUnit(packet.unit.getID()).setPosition(packet.newPosition);
        }
    }
}
