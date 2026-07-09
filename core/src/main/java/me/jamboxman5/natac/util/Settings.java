package me.jamboxman5.natac.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.jamboxman5.natac.sfx.MusicTracks;

public class Settings {

    //SOUND SETTINGS
    public static float sfxVolume = .15f;
    public static float musVolume = .15f;

    //DISPLAY SETTINGS
    public static int screenWidth = 1366;
    public static int screenHeight = 768;
    public static float guiScale = 1.5f;

    public static float hudMargin = 20;

    public static Array<Resolution> resolutions = getAllResolutions();

    //GAME SETTINGS
    public static int defogTileRadius = 3;
    public static int mapRadius = 3;
    public static boolean debugMode = true;

    //MULTIPLAYER SETTINGS
    public static int maxPlayers = 2;
    public static int botDelayMS = 3000;
    public static boolean variableBotDelay = true;
    public static float delayRandomRatio = .3f;

    public static void getAvailableResolutions(Vector2 screenRes) {
        Array<Resolution> allResolutions = getAllResolutions();
        resolutions = new Array<>();
        for (Resolution res : allResolutions) {
            if (res.fits(screenRes)) resolutions.add(res);
        }
    }

    private static Array<Resolution> getAllResolutions() {
        return new Array<>(new Resolution[]{
                new Resolution(1024, 768),
                new Resolution(1280, 720),
                new Resolution(1280, 800),
                new Resolution(1280, 960),
                new Resolution(1366, 768),
                new Resolution(1440, 900),
                new Resolution(1600, 900),
                new Resolution(1600, 1200),
                new Resolution(1680, 1050),
                new Resolution(1920, 1080),
                new Resolution(1920, 1200),
                new Resolution(2560, 1080),
                new Resolution(2560, 1440),
                new Resolution(2560, 1600),
                new Resolution(3200, 1800),
                new Resolution(3280, 2160),
                new Resolution(3440, 1440),
                new Resolution(3840, 1080),
                new Resolution(3840, 1600),
                new Resolution(5120, 1440),
                new Resolution(5120, 2160),
                new Resolution(5120, 2880),
                new Resolution(7680, 4320),
        });
    }

    public static Resolution getResolution() {
        for (Resolution res : resolutions) {
            if (res.equals(new Vector2(screenWidth, screenHeight))) return res;
        }
        return new Resolution(-1, -1);
    }

    public static void setResolution(Resolution res) {
        if (res.equals(new Vector2(Settings.screenWidth, Settings.screenHeight))) return;
        Gdx.graphics.setUndecorated(false);
        screenWidth = Math.round(res.getX());
        screenHeight = Math.round(res.getY());
        Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
        if (Gdx.graphics.getWidth() != screenWidth || Gdx.graphics.getHeight() != screenHeight) {
            Gdx.graphics.setUndecorated(true);
            Gdx.graphics.setWindowedMode(screenWidth, screenHeight);
        }
    }

    public static void setMusicVolume(float newVolume) {
        Settings.musVolume = newVolume;
        for (MusicTracks m : MusicTracks.values()) {
            m.setVolume(Settings.musVolume);
        }
    }

    public static void setSfxVolume(float newVolume) {
        Settings.sfxVolume = newVolume;
    }

    public static class Resolution {

        private final Vector2 res;

        public Resolution(int x, int y) {
            this.res = new Vector2(x, y);
        }

        public boolean fits(Vector2 screenRes) {
            return (res.x <= screenRes.x && res.y <= screenRes.y);
        }

        public boolean equals(Vector2 otherRes) {
            return ((otherRes.x == res.x && otherRes.y == res.y) && (Gdx.graphics.getWidth() == res.x && Gdx.graphics.getHeight() == res.y));
        }

        public float getX() { return res.x; }
        public float getY() { return res.y; }

        public String toString() {
            return ((int) res.x) + "x" + ((int)res.y);
        }
    }
}
