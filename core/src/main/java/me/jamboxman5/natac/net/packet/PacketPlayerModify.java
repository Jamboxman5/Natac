package me.jamboxman5.natac.net.packet;

public class PacketPlayerModify extends Packet {
    public String sendPlayerID;
    public String modPlayerID;

    public int diffResearch;
    public int diffStatus;
    public int diffAttack;
    public int diffDefense;

    public int diffGold;
    public int diffResources;
}
