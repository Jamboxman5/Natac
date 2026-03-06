package me.jamboxman5.natac.structures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Structure {
    int buildCost;
    int revenuePerTurn;

    protected Structure(int buildCost, int revenuePerTurn) {
        this.buildCost = buildCost;
        this.revenuePerTurn = revenuePerTurn;
    }

    public abstract void update();
    public abstract void draw(SpriteBatch batch, ShapeDrawer shapes);
}
