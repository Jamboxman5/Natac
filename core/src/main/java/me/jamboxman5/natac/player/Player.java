package me.jamboxman5.natac.player;

import com.badlogic.gdx.graphics.Color;

import java.util.List;
import java.util.UUID;

public class Player {

    private PlayerClass playerClass;

    List inventory;

    private int gold;
    private int resources;

    private int research;
    private int status;
    private int attack;
    private int defense;

    private Color tileColor;

    private String username;
    private UUID id;

    public Player(String username, PlayerClass playerClass, Color tileColor) {
        this.username = username;
        this.tileColor = tileColor;
        this.id = UUID.randomUUID();
        this.playerClass = playerClass;

        this.attack = playerClass.attack;
        this.defense = playerClass.defense;
        this.gold = playerClass.gold;
        this.status = playerClass.status;
        this.research = playerClass.research;

        this.resources = 100;

    }

    public String getUsername() { return username; }
    public UUID getID() { return id; }

    public void draw() {

    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
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


    public PlayerClass getPlayerClass() { return playerClass; }
}
