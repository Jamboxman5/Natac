package me.jamboxman5.natac.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import me.jamboxman5.natac.util.Settings;

public enum Sounds {

    TILE_HOVER(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/tile_hover.mp3")));

    public final Sound sound;

    public void play() {
        this.sound.play(Settings.sfxVolume);
    }

    Sounds(Sound sound) {
        this.sound = sound;
    }
}
