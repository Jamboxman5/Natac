package me.jamboxman5.natac.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import me.jamboxman5.natac.util.Settings;

public enum Sounds {

    TILE_HOVER(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/tile_hover.mp3"))),
    MENU_SLIDE_IN(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/menu_slide_in.mp3"))),
    MENU_SLIDE_OUT(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/menu_slide_out.mp3")));

    public final Sound sound;

    public void play() {
        this.sound.play(Settings.sfxVolume);
    }

    Sounds(Sound sound) {
        this.sound = sound;
    }
}
