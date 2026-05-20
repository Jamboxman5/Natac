package me.jamboxman5.natac.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class UIManager {

    public static void draw(SpriteBatch batch, ShapeDrawer shapes, GameScreen.State gameState) {
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, Natac.instance.player.getUsername(), batch, 20, 40);

        int statStart = Settings.screenHeight - 40;

        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Money: $" + Natac.instance.player.getMoney(), batch, 20, statStart);

        switch(gameState) {
            case PLAY:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Your turn... ", batch, 20, 80);
                break;
            case WAIT:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Waiting for other players... ", batch, 20, 80);
                break;
            case CLAIM:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Select a starting tile. ", batch, 20, 80);
                break;

        }

    }

    private static void drawPlayUI(SpriteBatch batch, ShapeDrawer drawer) {

    }



}
