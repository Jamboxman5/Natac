package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.structures.Structure;

import java.util.UUID;

public class PacketRemoveStructure extends Packet {
    public Structure structure;
    public Vector2 tilePos;
}
