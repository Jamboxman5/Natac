package me.jamboxman5.natac.entity.structures.prop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Tree extends Prop {

    protected int treeVersion = 1;

    public Tree() {
    }

    public Tree(Vector2 tilePosition, Vector2 positionInTile) {
        super(positionInTile, tilePosition, "Tree");
        this.spriteScale = (float) (0.8f + (Math.random() * .4));
        this.treeVersion = (int) ( 1 + (Math.random() * 2));
    }

    @Override
    public void update() {
        if (sprite == null) {
            initGraphics(new Texture(Gdx.files.internal("structure/prop/tree_" + treeVersion + ".png")), spriteYOffset, spriteScale);
        }
    }

}
