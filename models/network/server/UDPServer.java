package models.network.server;

import models.network.server.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class UDPServer extends Server {

    private DatagramSocket socket;

    private byte[] tampon;

    private DatagramPacket receiver;

    /**
     * État initial de l'objet
     * @param port Le port sur lequel tourne le serveur
     */
    public UDPServer(int port) {
        super("UDP", port);
    }

    @Override
    public void startServer() throws IOException {
        init();

        listen();
    }

    @Override
    public void init() throws IOException {
        tampon = new byte[1024];
        socket = new DatagramSocket(getPort());
        receiver = new DatagramPacket(tampon, tampon.length);
    }

    /**
     * Coeur du serveur. Recupère, distribue et renvoie les requêtes
     * @throws IOException
     */
    private void listen() throws IOException {
        while (true) {
            socket.receive(receiver);

            String input = new String(receiver.getData(), 0, receiver.getLength());

            System.out.println("Incoming request ...");
            String output = onRequest(receiver, input);
            System.out.println(output + "\n");

            DatagramPacket response = new DatagramPacket(output.getBytes(), output.getBytes().length,
                    receiver.getAddress(), receiver.getPort());

            socket.send(response);

            receiver.setLength(tampon.length);
        }
    }

    /**
     * Business logic when incoming request
     * @param receiver
     * @param request
     * @return String response
     */
    public abstract String onRequest(DatagramPacket receiver, String request);
}
