package me.jamboxman5.natac.net;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import me.jamboxman5.natac.Natac;
import me.jamboxman5.natac.net.packet.Packet;
import me.jamboxman5.natac.net.packet.PacketCloseGame;
import me.jamboxman5.natac.net.packet.PacketDisconnect;
import me.jamboxman5.natac.net.packet.PacketLogin;
import me.jamboxman5.natac.player.Player;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ClientManager {

    Client client;
    Natac game;

    Array<String> connectedPlayers;
    HashMap<String, String> connectedPlayerNames;

    public ClientManager(Natac game) {
        this.game = game;
    }

    public void sendPacketTCP(Packet packet) {
        client.sendTCP(packet);
    }
    public void sendPacketUDP(Packet p) { client.sendUDP(p); }

    public void connect(Player player, String address) throws IOException {
        if (address.length() == 0) address = "localhost";
        client = new Client(65536, 65536);
        Kryo kryo = client.getKryo();

        connectedPlayers = new Array<>();
        connectedPlayerNames = new HashMap<>();

        NetUtil.registerPackets(kryo);
        NetUtil.registerListeners(client);
        client.start();

        client.connect(5000, address, 13531, 13531);
        sendLogin(player);

    }

    private void sendLogin(Player player) {
        PacketLogin login = new PacketLogin();
        login.username = player.getUsername();
        login.uuid = player.getID();
        client.sendTCP(login);
        connectedPlayers.add(player.getID());
        connectedPlayerNames.put(player.getID(), player.getUsername());
    }

    public void disconnectPlayer(PacketDisconnect disconnect) {
        connectedPlayers.removeValue(disconnect.uuid, false);
        connectedPlayerNames.remove(disconnect.uuid);
//        game.getMapManager().removeOnlinePlayer(disconnect);
    }

    public void connectPlayer(PacketLogin login) {
        connectedPlayers.add(login.uuid);
        connectedPlayerNames.put(login.uuid, login.username);
    }

    public boolean isConnected() {
        return (client != null && client.isConnected());
    }

    public Array<String> getConnectedPlayers() {
        if (connectedPlayers == null) return new Array<>();
        else return connectedPlayers;
    }

    public String getConnectedPlayerName(String uuid) {
        if (connectedPlayerNames.containsKey(uuid)) return connectedPlayerNames.get(uuid);
        else return "PLAYER NOT FOUND";
    }

    public void disconnect(Player p) {

        if (game.isHosting()) {
            PacketCloseGame packet = new PacketCloseGame();
            packet.username = p.getUsername();
            packet.message = "The host has closed the game!";
            sendPacketTCP(packet);
            game.stopGame();
        }

        if (client == null) return;
        client.stop();
        client.close();
        try {
            client.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = null;
        connectedPlayerNames = null;
        connectedPlayers = null;

    }
}
