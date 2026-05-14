package me.jamboxman5.natac.net.listener.client;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.PacketStartGame;
import me.jamboxman5.natac.screen.GameScreen;

public class PacketStartGameListener implements Listener {
    @Override
    public void received(Connection conn, Object obj) {
        if (obj instanceof PacketStartGame) {
            PacketStartGame packet = (PacketStartGame) obj;
            Gdx.app.postRunnable(() -> {
                Natac.instance.setScreen(new GameScreen(packet.map));

            });
        }
    }
}
