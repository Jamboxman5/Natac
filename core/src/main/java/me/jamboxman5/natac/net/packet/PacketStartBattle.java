package me.jamboxman5.natac.net.packet;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class PacketStartBattle extends Packet {
    public Vector2 tilePos;
    public HashSet<UUID> fightingPlayers;
}
