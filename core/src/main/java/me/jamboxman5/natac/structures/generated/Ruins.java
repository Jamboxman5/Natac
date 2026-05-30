package me.jamboxman5.natac.structures.generated;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.structures.Structure;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Ruins extends Structure {

    public Ruins() {
        this.drawColor = Color.BROWN;
    }

    public Ruins(Vector2 tilePos) {
        super(1, 1, 1, tilePos);
    }

    @Override
    public void update() {

    }

}
