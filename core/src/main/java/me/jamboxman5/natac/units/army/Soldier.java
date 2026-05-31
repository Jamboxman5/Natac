package me.jamboxman5.natac.units.army;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.units.Unit;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.UUID;

public class Soldier extends Unit {

    protected int health;
    protected int damage;
    protected int defense;

    public Soldier() {}

    public Soldier(Vector2 tilePos, Vector2 position, UUID owner) {
        super(1, 1, tilePos, position, owner);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes) {
        shapes.setColor(Color.BLUE);
        shapes.filledCircle(tilePos.cpy().add(position), 5);
    }
}
