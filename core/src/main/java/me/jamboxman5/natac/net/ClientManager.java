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


import javax.swing.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.UUID;

public class ClientManager {

    Client client;
    Natac game;

    Array<Player> connectedPlayers;

    public ClientManager(Natac game) {
        this.game = game;
    }

    public void sendPacketTCP(Packet packet) {
        client.sendTCP(packet);
//        log(packet.toString());
    }
    public void sendPacketUDP(Packet p) { client.sendUDP(p); }

    public void connect(Player player, String address) throws IOException {
        if (address == null) return;
        if (address.isEmpty()) address = "localhost";
        client = new Client(65536, 65536);
        Kryo kryo = client.getKryo();

        connectedPlayers = new Array<>();

        NetUtil.registerPackets(kryo);
        NetUtil.registerListeners(client);
        client.start();

        client.connect(5000, address, 13531, 13531);
        sendLogin(player);

    }

    private void sendLogin(Player player) {
        PacketLogin login = new PacketLogin();
        login.connectingPlayer = player;
        client.sendTCP(login);
        connectedPlayers.add(player);
    }

    public void disconnectPlayer(PacketDisconnect disconnect) {
        Player removing = null;
        for (Player p : connectedPlayers) {
            if (p.getID().equals(disconnect.uuid)) removing = p;
        }
        connectedPlayers.removeValue(removing, false);
    }

    public void connectPlayer(PacketLogin login) {
        connectedPlayers.add(login.connectingPlayer);
    }

    public boolean isConnected() {
        return (client != null && client.isConnected());
    }

    public Array<Player> getConnectedPlayers() {
        if (connectedPlayers == null) return new Array<>();
        else return connectedPlayers;
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
        connectedPlayers = null;

    }

    public void log(String log) {
        System.out.println("\u001B[32mCLIENT:: " + log + "\u001B[0m");
    }
    public void logSevere(String log) {
        System.out.println("\u001B[31mCLIENT WARNING::  " + log + "\u001B[0m");
    }

    public Player findPlayer(UUID playerID) {
        for (Player p : connectedPlayers) {
            if (p.getID().equals(playerID)) return p;
        }
        return null;
    }
}
