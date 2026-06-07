package me.jamboxman5.natac.units.army;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.units.Unit;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.UUID;

public class Soldier extends Unit {

    protected int health;
    protected int damage;
    protected int defense;

    public Soldier() {
        this.color = Color.CYAN;
    }

    public Soldier(Vector2 tilePos, Vector2 position, UUID owner) {
        super(1, 1, tilePos, position, Color.CYAN, owner);
    }

    @Override
    public void update() {
        super.update();
    }
}
