package me.jamboxman5.natac.player;

import me.jamboxman5.natac.map.Tile;

import java.util.List;

public class Player {

    private Class playerClass;
    List<Tile> territory;
    List inventory;

    private int money;
    private int resources;
    private int research;

    public enum Class {
        HOLYEMPIRE, MOLEPEOPLE, BARBARIAN, STEELCITY, GOLDENKEEP, NECROPOLIS;
    }
}
