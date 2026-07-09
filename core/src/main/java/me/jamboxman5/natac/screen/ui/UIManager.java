package me.jamboxman5.natac.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.screen.GameScreen;
import me.jamboxman5.natac.util.Settings;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class UIManager {

    public static void draw(SpriteBatch batch, ShapeDrawer shapes, GameScreen.State gameState, GameScreen.SelectionMode selectionMode) {

        int statStart = Settings.screenHeight - 40;

        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Gold: $" + Natac.instance.player.getGold(), batch, 20, statStart);
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Resources: " + Natac.instance.player.getResources(), batch, 20, statStart - 40);
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Research: " + Natac.instance.player.getResearch(), batch, 20, statStart - 80);

        switch(gameState) {
            case PLAY:
                if (Natac.instance.getGame().getTileSelectionMode() == GameScreen.SelectionMode.TRAVEL) {
                    Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Send this unit where? ", batch, 20, 80);
                } else {
                    Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Your turn... ", batch, 20, 80);
                }
                break;
            case WAIT:
                Player turnPlayer = Natac.instance.getClientManager().getCurrentTurn();
                if (turnPlayer != null) {
                    Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, turnPlayer.getUsername() + "'s turn... ", batch, 20, 80);
                } else {
                    Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Waiting for other players... ", batch, 20, 80);
                }
                break;
            case CLAIM:
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "Select a starting tile. ", batch, 20, 80);
                break;

        }

        if (Natac.instance.getClientManager().getCurrentTurn() == null) return;

        Array<Player> players = Natac.instance.getClientManager().getConnectedPlayers();
        float y = 100 + (40 * players.size);
        Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "-- Player Queue --", batch, Fonts.getXForRightAlignedText(Settings.screenWidth - 20, "-- Player Queue --", Fonts.PLACEHOLDER_FONT, 1f), y);
        y -= 60;
        for (Player p : players) {
            if (Natac.instance.getClientManager().getCurrentTurn().getID().equals(p.getID())) {
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, "> " + p.getUsername(), batch, Fonts.getXForRightAlignedText(Settings.screenWidth - 20, "> " + p.getUsername(), Fonts.PLACEHOLDER_FONT, 1f), y);
            } else {
                Fonts.drawScaled(Fonts.PLACEHOLDER_FONT, 1f, p.getUsername(), batch, Fonts.getXForRightAlignedText(Settings.screenWidth - 20, p.getUsername(), Fonts.PLACEHOLDER_FONT, 1f), y);
            }
            y-=40;
        }



    }

    private static void drawPlayUI(SpriteBatch batch, ShapeDrawer drawer) {

    }



}
