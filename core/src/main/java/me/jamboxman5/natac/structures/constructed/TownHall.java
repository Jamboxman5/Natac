package me.jamboxman5.natac.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Rectangle;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.structures.Structure;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TownHall extends Structure {

    protected PlayerClass type;

    public TownHall() {}

    public TownHall(PlayerClass playerClass, Tile location) {
        super(0, 50, 0, location);
        this.type = playerClass;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(SpriteBatch batch, ShapeDrawer shapes) {
        shapes.setColor(Color.PINK);
        shapes.filledRectangle(new Rectangle(position.x, position.y, 5, 5));
    }
}
