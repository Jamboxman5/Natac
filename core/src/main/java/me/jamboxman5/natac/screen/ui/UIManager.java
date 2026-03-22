package me.jamboxman5.natac.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import me.jamboxman5.natac.screen.GameScreen;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class UIManager {

    public static void draw(SpriteBatch batch, ShapeDrawer shapes, GameScreen.State gameState) {
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, GameScreen.player.getUsername(), batch, 20, 40);

        switch(gameState) {
            case PLAY:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Your turn... ", batch, 20, 80);
                break;
            case WAIT:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Waiting for other players... ", batch, 20, 80);
                break;
            case CLAIM:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Select a tile to start. ", batch, 20, 80);
                break;
        }

    }

    private static void drawPlayUI(SpriteBatch batch, ShapeDrawer drawer) {

    }



}
