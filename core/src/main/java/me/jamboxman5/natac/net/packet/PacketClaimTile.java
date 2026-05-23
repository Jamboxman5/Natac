package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.Map;

import java.util.UUID;

public class PacketClaimTile extends Packet {
    public Vector2 tilePos;
    public UUID claimingID;
    public boolean finalClaim = false;
}
