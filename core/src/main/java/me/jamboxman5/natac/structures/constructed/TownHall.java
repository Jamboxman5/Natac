package me.jamboxman5.natac.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.map.tile.Tile;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.structures.Structure;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TownHall extends Structure {

    protected PlayerClass type;

    public TownHall() {}

    public TownHall(PlayerClass playerClass, Vector2 tilePos) {
        super(0, 50, 0, tilePos);
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
