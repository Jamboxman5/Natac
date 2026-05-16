package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.Map;

public class PacketClaimTile extends Packet {
    public Vector2 tilePos;
    public String claimingID;
}
