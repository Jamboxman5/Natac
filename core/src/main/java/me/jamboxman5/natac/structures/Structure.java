package me.jamboxman5.natac.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Structure {

    protected int buildCost;

    protected int revenuePerTurn;
    protected int resourcesPerTurn;

    protected Vector2 tilePos;

    protected Vector2 position;

    protected Structure() {

    }

    protected Structure(int buildCost, int revenuePerTurn, int resourcesPerTurn, Vector2 tilePos) {
        this.buildCost = buildCost;
        this.revenuePerTurn = revenuePerTurn;
        this.resourcesPerTurn = resourcesPerTurn;

        this.tilePos = tilePos;
        this.position = tilePos;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);
}
