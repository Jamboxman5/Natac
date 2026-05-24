package me.jamboxman5.natac.data;

import com.badlogic.gdx.Gdx;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.jamboxman5.natac.player.Player;

import javax.swing.filechooser.FileSystemView;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;

public class DataManager {

    static private final int shiftKey = 1;
    static final String dataPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/PAT-Interactive/Natac/data/";

    private static String shiftLoad(InputStream is) throws IOException {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        StringBuilder shiftedContents = new StringBuilder(result);
        for (int i = 0; i < shiftedContents.length(); i ++) {
            shiftedContents.setCharAt(i, (char) (shiftedContents.charAt(i) - shiftKey));
        }
        return shiftedContents.toString();
    }
    protected static String load(InputStream is) throws IOException {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    protected static void save(String jsonString, String path) throws IOException {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    private static void shiftSave(String jsonString, String path) {
        StringBuilder fileContents = new StringBuilder(jsonString);
        for (int i = 0; i < fileContents.length(); i ++) {
            fileContents.setCharAt(i, (char) (fileContents.charAt(i) + shiftKey));
        }
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(fileContents.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

}
