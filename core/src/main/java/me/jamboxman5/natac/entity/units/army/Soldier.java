package me.jamboxman5.natac.entity.units.army;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.units.Unit;

import java.util.UUID;

public class Soldier extends Unit {

    public static final int goldCost = 50;
    public static final int resourceCost = 0;

    public static final int attackCooldownMS = 1000;
    public static final int baseDamage = 10;
    public static final int attackForce = 100;
    public static final int range = 10;

    public static final float speed = 1;
    public static final int maxHealth = 100;

    public Soldier() {
        this.color = Color.CYAN;
    }

    public Soldier(Vector2 tilePos, Vector2 position, UUID owner) {
        super(speed, range, maxHealth, baseDamage, attackCooldownMS, attackForce, tilePos, position, Color.CYAN, owner);
    }

    @Override
    public void update() {
        super.update();
    }
}
