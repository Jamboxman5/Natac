package me.jamboxman5.natac.entity.structures.prop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Tree extends Prop {

    public Tree() {
    }

    public Tree(Vector2 tilePosition, Vector2 positionInTile) {
        super(positionInTile, tilePosition, "Tree");
    }

    @Override
    public void update() {
        if (sprite == null) {
            sprite = new Sprite(new Texture(Gdx.files.internal("structure/prop/tree_" + (int) ( 1 + (Math.random() * 2)) + ".png")));

        }
    }

}
