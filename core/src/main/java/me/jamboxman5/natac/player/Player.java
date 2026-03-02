package me.jamboxman5.natac.player;

import me.jamboxman5.natac.map.tile.Tile;

import java.util.List;
import java.util.UUID;

public class Player {

    private Class playerClass;
    List<Tile> territory;
    List inventory;

    private int money;
    private int resources;
    private int research;

    private String username;
    private UUID id;

    public String getUsername() { return username; }
    public String getID() { return id.toString(); }

    public void draw() {

    }

    public enum Class {
        HOLYEMPIRE, MOLEPEOPLE, BARBARIAN, STEELCITY, GOLDENKEEP, NECROPOLIS;
    }
}
