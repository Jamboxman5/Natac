package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.Entity;
import me.jamboxman5.natac.entity.units.Unit;

public class PacketDamageEntity extends Packet {
    public Entity entity;
    public int healthDiff;
}
