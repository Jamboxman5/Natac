package me.jamboxman5.natac.player;

import com.badlogic.gdx.graphics.Color;

public enum Class {

    HOLY_EMPIRE(1,2,2,0,1),
    MOLE_PEOPLE(1,2,2,0,1),
    BARBARIANS(1,2,2,0,1),
    STEEL_CITY(1,2,2,0,1),
    GOLDEN_KEEP(1,2,2,0,1),
    NECROPOLIS(1,2,2,0,1);

    public final int gold;
    public final int attack;
    public final int defense;
    public final int status;
    public final int research;

    Class(int attack, int defense, int status, int research, int gold) {
        this.gold = gold;
        this.defense = defense;
        this.attack = attack;
        this.status = status;
        this.research = research;
    }
}
