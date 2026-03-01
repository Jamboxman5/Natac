package me.jamboxman5.natac.map;

import java.util.List;
import java.util.UUID;

public class Tile {
    private int passability;
    private UUID owner;
    private TileType type;
    private int yield;
    private int level;

    private int health;
    private int defense;

    private List contents;

    public enum TileType {
        PLAINS, FOREST, RADIATION;
    }
}


