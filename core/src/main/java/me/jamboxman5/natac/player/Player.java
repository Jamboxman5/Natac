package me.jamboxman5.natac.player;

import com.badlogic.gdx.graphics.Color;
import me.jamboxman5.natac.map.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {

    private PlayerClass playerClass;

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

    }

    public String getUsername() { return username; }
    public String getID() { return id.toString(); }

    public void draw() {

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getResearch() {
        return research;
    }

    public void setResearch(int research) {
        this.research = research;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }


}
