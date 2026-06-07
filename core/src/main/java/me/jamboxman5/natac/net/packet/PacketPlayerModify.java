package me.jamboxman5.natac.net.packet;

import java.util.UUID;

public class PacketPlayerModify extends Packet {
    public UUID sendPlayerID;
    public UUID modPlayerID;

    public int diffResearch;
    public int diffStatus;
    public int diffAttack;
    public int diffDefense;

    public int diffGold;
    public int diffResources;

    public String toString() {
                    return diffAttack + " | " +
                                diffDefense + " | " +
                diffGold + " | " +
                diffResources + " | " +
                diffStatus + " | " +
                diffResearch;
    }
}
