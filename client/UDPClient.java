package client;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UDPClient extends Thread {

    private int port;

    private DatagramSocket socket;

    private final byte[] tampon = new byte[1024];

    private DatagramPacket receiver;

    private Scanner scanner;

    private InetAddress destination;

    public UDPClient(int port) throws IOException {
        this.port = port;

        launchServer();
    }

    public void launchServer() throws IOException {
        initSocket();

        initReceiver();

        initScanner();

        initDestination();

        live();
    }

    private void initSocket() throws SocketException {
        socket = new DatagramSocket();
    }

    private void initReceiver() {
        receiver = new DatagramPacket(tampon, tampon.length);
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    private void initDestination() throws UnknownHostException {
        destination = InetAddress.getByName("localhost");
    }

    private DatagramPacket createRequestFromString(String toAdd) {
        byte [] octets = toAdd.getBytes();
        return new DatagramPacket(octets, octets.length, destination, port);
    }

    private void live() throws IOException {
        String chaine = "";
        System.out.println("Tapez vos phrases ou FIN pour arrêter :");

        while (!chaine.equalsIgnoreCase("FIN")) {
            // lecture clavier
            chaine = scanner.nextLine();

            DatagramPacket request = createRequestFromString(chaine);
            socket.send(request);

            // attente et réception d'un datagramme UDP
            socket.receive(receiver);

            // extraction des données
            String res = new String(receiver.getData(), 0, receiver.getLength());

            System.out.println("Response : " + res);

            // on replace la taille du tampon au max
            // elle a été modifiée lors de la réception
            receiver.setLength(tampon.length);
        }
    }

    public static void main(String[] args) throws Exception {
        UDPClient UDPClient = new UDPClient(40000);
    }
}
