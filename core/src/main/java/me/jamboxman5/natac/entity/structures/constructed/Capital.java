package me.jamboxman5.natac.entity.structures.constructed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import me.jamboxman5.natac.player.PlayerClass;
import me.jamboxman5.natac.entity.structures.Structure;

public class Capital extends Structure {

    protected PlayerClass type;

    public static final String barbarianCapitalPath = "structure/capital/capital_barbarians.png";
    public static final String goldenKeepCapitalPath = "structure/capital/capital_goldenkeep.png";
    public static final String necropolisCapitalPath = "structure/capital/capital_necropolis.png";
    public static final String steelCityCapitalPath = "structure/capital/capital_steelcity.png";
    public static final String holyEmpireCapitalPath = "structure/capital/capital_holyempire.png";
    public static final String molePeopleCapitalPath = "structure/capital/capital_molepeople.png";

    public Capital() {
        this.drawColor = Color.PINK;
        this.structureName = "Town Hall";
    }

    public Capital(PlayerClass playerClass, Vector2 tilePos) {
        super(0, 0, 50, 0, 100, tilePos, new Vector2(0, 0), "Town Hall");
        this.type = playerClass;
    }

    @Override
    public void update() {
        if (sprite == null) {
            initClassGraphics();
        }
    }

    protected void initClassGraphics() {
        switch(type) {
            case BARBARIANS:
                initGraphics(new Texture(barbarianCapitalPath), -4, .5f);
                break;
            case STEEL_CITY:
                initGraphics(new Texture(steelCityCapitalPath), 0, 1f);
                break;
            case MOLE_PEOPLE:
                initGraphics(new Texture(molePeopleCapitalPath), 0, 1f);
                break;
            case GOLDEN_KEEP:
                initGraphics(new Texture(goldenKeepCapitalPath), 0, 1f);
                break;
            case HOLY_EMPIRE:
                initGraphics(new Texture(holyEmpireCapitalPath), 0, 1f);
                break;
            case NECROPOLIS:
                initGraphics(new Texture(necropolisCapitalPath), 0, 1f);
                break;
        }
    }

}
