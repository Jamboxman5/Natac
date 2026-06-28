package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class Capital extends Structure {

    protected PlayerClass type;

    public static final Texture barbarianCapital = new Texture("structure/capital/capital_barbarians.png");

    public Capital() {
        this.drawColor = Color.PINK;
        this.structureName = "Town Hall";
    }

    public Capital(PlayerClass playerClass, Vector2 tilePos) {
        super(0, 0, 50, 0, 100, tilePos, new Vector2(0, 0), "Town Hall");
        this.type = playerClass;
        initGraphics();
    }

    @Override
    public void update() {
        if (sprite == null) initGraphics();
    }

    private void initGraphics() {
        switch(type) {
            case BARBARIANS:
                sprite = new Sprite(barbarianCapital);
                this.spriteYOffset = -12;
                this.spriteScale = .5f;
                break;
        }
    }

}
