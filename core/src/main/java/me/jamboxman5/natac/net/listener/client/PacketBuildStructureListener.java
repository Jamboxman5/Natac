package me.jamboxman5.natac.net.listener.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketBuildStructure;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketEndTurn;

public class PacketBuildStructureListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketBuildStructure) {
            PacketBuildStructure packet = (PacketBuildStructure) obj;
            Natac.instance.getGame().getMap().findTile(packet.tilePos).add(packet.structure);
        }
    }
}
