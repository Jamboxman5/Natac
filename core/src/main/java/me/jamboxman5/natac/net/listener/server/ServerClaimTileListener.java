package me.jamboxman5.natac.net.listener.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.net.DiscreteServer;
import me.jamboxman5.natac.net.packet.PacketClaimTile;
import me.jamboxman5.natac.net.packet.PacketLogin;
import me.jamboxman5.natac.net.packet.PacketLoginRejected;

public class ServerClaimTileListener implements Listener {

    DiscreteServer server;

    public ServerClaimTileListener(DiscreteServer server) {
        this.server = server;
    }

    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketClaimTile) {
            PacketClaimTile packet = (PacketClaimTile) obj;
            server.log("Tile claimed at " + packet.tilePos.x + ", " + packet.tilePos.y + " by " + server.findPlayer(packet.claimingID) + ".");



            server.getServer().sendToAllExceptTCP(conn.getID(), packet);
            for (Connection c : server.getServer().getConnections()) {
                if (c.getID() != conn.getID()) {
                    server.getServer().sendToTCP(conn.getID(), server.connections.get(c));
                }
            }

        }
    }
}
