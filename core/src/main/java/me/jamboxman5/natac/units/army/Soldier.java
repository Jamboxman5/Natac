package me.jamboxman5.natac.units.army;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.units.Unit;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Soldier extends Unit {

    protected int health;
    protected int damage;
    protected int defense;

    public Soldier(Tile location) {
        super(1, 1, location);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes) {
        shapes.circle(200, 200, 5);
    }
}
