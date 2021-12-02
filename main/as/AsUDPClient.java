package main.as;

import models.ConsoleScanner;
import models.network.client.UDPClient;
import main.Config;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AsUDPClient extends ConsoleScanner {

    private UDPClient udpClient;

    /**
     * État initial de l'objet
     * @param host L'host
     * @param port Le port sur lequel on souhaite communiquer
     * @throws SocketException
     * @throws UnknownHostException
     */
    public AsUDPClient(String host, int port) throws SocketException, UnknownHostException {
        udpClient = new UDPClient(host, port);
    }

    /**
     * Lance le Scanner dans la console
     */
    public void run() {
        super.askUserInput();
    }

    @Override
    public void onUserInput(String input) {
        try {
            udpClient.sendMessage(input);
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de l'envoie du message " + e.getMessage());
        }

        System.out.println(udpClient.readResponse());
    }

    @Override
    public void onUserLeave() {
        System.out.println("Déjà ... ?");
        udpClient.stop();
    }

    public static void main(String[] args) {
        try {
            AsUDPClient client = new AsUDPClient("localhost", Config.getAsServerPort());
            client.run();
        } catch (Exception e) {
            System.out.println("Une erreur est survenue lors de l'instanciation, " + e.getMessage());
        }
    }
}
