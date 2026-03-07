package me.jamboxman5.natac.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Structure {
    protected int buildCost;
    protected int revenuePerTurn;
    protected Tile location;

    protected Structure(int buildCost, int revenuePerTurn, Tile location) {
        this.buildCost = buildCost;
        this.revenuePerTurn = revenuePerTurn;
        this.location = location;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);
}
