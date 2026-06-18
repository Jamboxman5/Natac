package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.units.Unit;

public class PacketRepositionUnit extends Packet {
    public Unit unit;
    public Vector2 newPosition;
}
