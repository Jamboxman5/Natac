package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.structures.Structure;
import me.jamboxman5.natac.units.Unit;

import java.util.UUID;

public class PacketMoveUnit extends Packet {
    public Unit unit;
    public Vector2 tilePosTo;
    public Vector2 tilePosFrom;
}
