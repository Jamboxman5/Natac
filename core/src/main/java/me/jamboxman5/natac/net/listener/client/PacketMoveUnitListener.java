package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.Map;
import me.jamboxman5.natac.net.packet.PacketMoveMob;

public class PacketMoveUnitListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketMoveMob) {
            PacketMoveMob packet = (PacketMoveMob) obj;
            Map m = Natac.instance.getGame().getMap();
            m.findUnit(packet.mob.getID()).move(m.findTile(packet.tilePosFrom), m.findTile(packet.tilePosTo));
        }
    }
}
