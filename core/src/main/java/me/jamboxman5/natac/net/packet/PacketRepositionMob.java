package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.units.Mob;
import me.jamboxman5.natac.entity.units.Unit;

public class PacketRepositionMob extends Packet {
    public Mob mob;
    public Vector2 newPosition;
}
