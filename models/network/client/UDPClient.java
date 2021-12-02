package models.network.client;

import models.enums.TypeResponse;

import java.io.IOException;
import java.net.*;

public class UDPClient {

    private DatagramSocket socket;

    private final byte[] tampon = new byte[1024];

    private DatagramPacket receiver;

    private InetAddress destination;

    private int port;

    /**
     * État initial de l'objet
     * @param host L'host
     * @param port Le port sur lequel on souhaite communiquer
     * @throws SocketException
     * @throws UnknownHostException
     */
    public UDPClient(String host, int port) throws SocketException, UnknownHostException {
        this.port = port;

        socket = new DatagramSocket();
        receiver = new DatagramPacket(tampon, tampon.length);
        destination = InetAddress.getByName(host);
    }

    /**
     * Envoie un message au serveur distant
     * @param message Le message à envoyer
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        byte [] octets = message.getBytes();
        DatagramPacket request = new DatagramPacket(octets, octets.length, destination, port);
        socket.send(request);
    }

    /**
     * Récupère la dernière réponse du serveur distant
     * @return la dernière réponse
     */
    public String readResponse() {
        String output;
        try {
            socket.receive(receiver);
            output = new String(receiver.getData(), 0, receiver.getLength());
        } catch (IOException e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }
        clear();
        return output;
    }

    /**
     * Coupe la communication avec le serveur distant
     */
    public void stop() {
        socket.close();
    }

    private void clear() {
        receiver.setLength(tampon.length);
    }
}
