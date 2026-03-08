package me.jamboxman5.natac.player;

import com.badlogic.gdx.graphics.Color;
import me.jamboxman5.natac.map.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {

    private Class playerClass;
    List<Tile> territory;
    List inventory;

    private int money;
    private int resources;
    private int research;
    private int status;
    private int attack;
    private int defense;

    private Color tileColor;

    private String username;
    private UUID id;

    public Player(String username, Color tileColor) {
        this.username = username;
        this.tileColor = tileColor;
        this.id = UUID.randomUUID();

        territory = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public String getID() { return id.toString(); }

    public void draw() {

    }

    public void giveTile(Tile tile) {
        territory.add(tile);
    }
}
