package me.jamboxman5.natac.entity.units.army;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.units.Unit;

import java.util.UUID;

public class Soldier extends Unit {

    protected int health;
    protected int damage;
    protected int defense;

    public static final int goldCost = 50;
    public static final int resourceCost = 0;

    public Soldier() {
        this.color = Color.CYAN;
    }

    public Soldier(Vector2 tilePos, Vector2 position, UUID owner) {
        super(1, 1, 100, tilePos, position, Color.CYAN, owner);
    }

    @Override
    public void update() {
        super.update();
    }
}
