package me.jamboxman5.natac.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import me.jamboxman5.natac.util.Settings;

public enum MusicTracks {

    TEXT_BGM(Gdx.audio.newMusic(Gdx.files.internal("sound/music/natac-test-bgm.wav")));

    public final Music track;

    public void play() {
        this.track.setLooping(true);
        this.track.setVolume(Settings.musVolume);
        this.track.play();
    }

    public void setVolume(float newVolume) {
        this.track.setVolume(newVolume);
    }

    public void stop() {
        this.track.stop();
    }

    MusicTracks(Music track) {
        this.track = track;
    }
}
