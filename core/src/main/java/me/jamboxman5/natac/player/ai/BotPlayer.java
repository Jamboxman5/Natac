package me.jamboxman5.natac.player.ai;

import com.badlogic.gdx.graphics.Color;
import me.jamboxman5.natac.player.Player;
import me.jamboxman5.natac.player.PlayerClass;

public class BotPlayer extends Player {

    public BotPlayer() {
        super("Player_" + (int) (Math.random() * 999), getRandomClass(), Color.RED);
    }

    public void update() {

    }

    private static PlayerClass getRandomClass() {
        PlayerClass[] classes = PlayerClass.values();
        return classes[(int) (Math.random() * classes.length)];
    }

}
