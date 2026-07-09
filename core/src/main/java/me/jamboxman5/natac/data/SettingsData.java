package me.jamboxman5.natac.data;

import com.badlogic.gdx.Gdx;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.jamboxman5.natac.util.Settings;

import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsData {

    static final String dataPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/PAT-Interactive/Natac/data/";
    static final String settingsFile = "settings.json";
    static final String settingsPath =  dataPath + settingsFile;

    public static boolean loadSettings() {

        String jsonString = null;
        try {
            InputStream is = Files.newInputStream(Paths.get(settingsPath));
            jsonString = DataManager.load(is);
            JsonObject settingsOBJ = JsonParser.parseString(jsonString).getAsJsonObject();
            bindSettings(settingsOBJ);
            return false;
        } catch (IOException e) {
            return true;
        }

    }

    @SuppressWarnings("NewApi")
    public static void generateNewSettingsFile() {
        try {
            Files.createDirectories(Paths.get(dataPath));
            InputStream defaultInput = Gdx.files.internal("data/settings/settings.json/").read();
            assert defaultInput != null;
            String json = DataManager.load(defaultInput);
            JsonObject settingsOBJ = JsonParser.parseString(json).getAsJsonObject();
            DataManager.save(settingsOBJ.toString(), settingsPath);
            bindSettings(settingsOBJ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void bindSettings(JsonObject settingsOBJ) {
        Settings.screenWidth = settingsOBJ.get("screenWidth").getAsInt();
        Settings.screenHeight = settingsOBJ.get("screenHeight").getAsInt();
        Settings.guiScale = settingsOBJ.get("guiScale").getAsFloat();
        Settings.musVolume = settingsOBJ.get("musVolume").getAsFloat();
        Settings.sfxVolume = settingsOBJ.get("sfxVolume").getAsFloat();
        Settings.hudMargin = settingsOBJ.get("hudMargin").getAsInt();
        Settings.defogTileRadius = settingsOBJ.get("defogTileRadius").getAsInt();
        Settings.mapRadius = settingsOBJ.get("mapRadius").getAsInt();
        Settings.maxPlayers = settingsOBJ.get("maxPlayers").getAsInt();
        Settings.botDelayMS = settingsOBJ.get("botDelay").getAsInt();
        Settings.botDelayRandom = settingsOBJ.get("botDelayRandom").getAsFloat();
    }

    public static void updateSettings() {
        JsonObject settingsOBJ = new JsonObject();
        settingsOBJ.addProperty("screenWidth", Settings.screenWidth);
        settingsOBJ.addProperty("screenHeight", Settings.screenHeight);
        settingsOBJ.addProperty("guiScale", Settings.guiScale);
        settingsOBJ.addProperty("hudMargin", Settings.hudMargin);
        settingsOBJ.addProperty("sfxVolume", Settings.sfxVolume);
        settingsOBJ.addProperty("musVolume", Settings.musVolume);
        settingsOBJ.addProperty("defogTileRadius", Settings.defogTileRadius);
        settingsOBJ.addProperty("mapRadius", Settings.mapRadius);
        settingsOBJ.addProperty("maxPlayers", Settings.maxPlayers);
        settingsOBJ.addProperty("botDelay", Settings.botDelayMS);
        settingsOBJ.addProperty("botDelayRandom", Settings.botDelayRandom);

        try {
            DataManager.save(settingsOBJ.toString(), settingsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
