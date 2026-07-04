package me.jamboxman5.natac.entity.units.army;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.entity.units.Unit;

import java.util.UUID;

public class Scout extends Unit {

    public static final int goldCost = 50;
    public static final int resourceCost = 0;

    public static final int attackCooldownMS = 1000;
    public static final int baseDamage = 0;
    public static final int attackForce = 50;
    public static final int range = 30;

    public static final float speed = 1f;
    public static final int maxHealth = 30;

    public Scout() {
        this.color = Color.PURPLE;
    }

    public Scout(Vector2 tilePos, Vector2 position, UUID owner) {
        super(speed, range, maxHealth, baseDamage, attackCooldownMS, attackForce, tilePos, position, Color.CYAN, owner);
    }

    @Override
    public void update() {
        super.update();
        if (sprite == null) {
            initGraphics(new Texture(Gdx.files.internal("unit/placeholder_unit.png")), 0, 1);
        }
    }
}
