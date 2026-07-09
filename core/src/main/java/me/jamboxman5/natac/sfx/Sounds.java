package me.jamboxman5.natac.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import me.jamboxman5.natac.util.Settings;

public enum Sounds {

    STRUCTURE_DROP(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/structure_drop.mp3"))),
    BATTLE_START(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/battle_start.mp3"))),
    RECEIVE_GOLD(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/receive_gold.mp3"))),
    VICTORY(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/victory.mp3"))),
    SELECT(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/select.mp3"))),
    UNIT_SPAWN(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/unit_spawn.mp3"))),
    TILE_HOVER(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/tile_hover.mp3"))),
    TILE_CLAIM(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/tile_claim.mp3"))),
    MENU_SLIDE_IN(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/menu_slide_in.mp3"))),
    MENU_SLIDE_OUT(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/menu_slide_out.mp3"))),
    START_TURN(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/start_turn.mp3"))),
    END_TURN(Gdx.audio.newSound(Gdx.files.internal("sound/sfx/end_turn.mp3")));

    public final Sound sound;

    public void play() {
        this.sound.play(Settings.sfxVolume);
    }

    Sounds(Sound sound) {
        this.sound = sound;
    }
}
