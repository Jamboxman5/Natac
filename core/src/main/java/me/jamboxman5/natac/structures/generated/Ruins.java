package me.jamboxman5.natac.structures.generated;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.structures.Structure;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Ruins extends Structure {

    public Ruins(Tile location) {
        super(1, 1, location);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes) {
        shapes.setColor(Color.WHITE);
        shapes.filledRectangle(new Rectangle(position.x, position.y, 5, 5));
    }
}
