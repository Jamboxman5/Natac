package me.jamboxman5.natac.net.packet;

public class Packet {
    public long timestamp;

    public Packet() {
        timestamp = System.currentTimeMillis();
    }
}
