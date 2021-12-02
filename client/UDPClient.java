package client;

import models.Client;
import utils.Config;

import java.io.IOException;
import java.net.*;

public class UDPClient extends Client {

    private DatagramSocket socket;

    private final byte[] tampon = new byte[1024];

    private DatagramPacket receiver;

    private InetAddress destination;

    public UDPClient(int port) throws IOException {
        super("UDP", port);

        start();
    }

    @Override
    public void init() throws IOException {
        super.init();
        socket = new DatagramSocket();
        receiver = new DatagramPacket(tampon, tampon.length);
        destination = InetAddress.getByName(getHost());
    }

    public void start() throws IOException {
        super.start();
        init();
    }

    public void handleInput(String input) throws IOException {
        byte [] octets = input.getBytes();
        DatagramPacket request = new DatagramPacket(octets, octets.length, destination, getPort());;
        socket.send(request);

        sendRequestToServer();
    }

    private void sendRequestToServer() throws IOException {
        socket.receive(receiver);
        String output = new String(receiver.getData(), 0, receiver.getLength());
        System.out.println("Response : " + output);

        clearReceiver();
    }

    private void clearReceiver() {
        receiver.setLength(tampon.length);
    }

    public void afterLive() {};

    public static void main(String[] args) throws Exception {
        UDPClient UDPClient = new UDPClient(Config.getAsServerPort());
    }
}
