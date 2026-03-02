package me.jamboxman5.natac.map.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    private Sprite sprite;

    public void draw(SpriteBatch batch) {

    }

    public enum TileType {
        PLAINS, FOREST, RADIATION;
    }
}


