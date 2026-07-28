package me.jamboxman5.natac.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import me.jamboxman5.natac.util.Settings;

public enum MusicTracks {

    TEST_BGM(Gdx.audio.newMusic(Gdx.files.internal("sound/music/temp_bgm.wav"))),
    MAIN_MENU_BGM(Gdx.audio.newMusic(Gdx.files.internal("sound/music/main_menu_bgm.wav"))),
    LOBBY_BGM(Gdx.audio.newMusic(Gdx.files.internal("sound/music/lobby_bgm.wav")));

    public final Music track;

    public static MusicTracks lastPlayed = null;

    public void play() {
        this.track.setLooping(true);
        this.track.setVolume(Settings.musVolume);
        this.track.play();
        lastPlayed = this;
    }

    public void setVolume(float newVolume) {
        this.track.setVolume(newVolume);
    }

    public void stop() {
        if (this.track.isPlaying()) this.track.stop();
    }

    public static void stopAll() {
        for (MusicTracks m : MusicTracks.values()) {
            m.stop();
        }
    }

    public boolean isPlaying() {
        return (this.track.isPlaying() || this.track.isLooping());
    }

    MusicTracks(Music track) {
        this.track = track;
    }
}
