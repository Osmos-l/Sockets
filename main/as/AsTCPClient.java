package main.as;

import models.ConsoleScanner;
import models.network.client.TCPClient;
import main.Config;

import java.io.IOException;

public class AsTCPClient extends ConsoleScanner {

    private TCPClient tcpClient;

    /**
     * État initial du projet
     * @param host
     * @param port Le port sur lequel lancer le client
     * @throws IOException
     */
    public AsTCPClient(String host, int port) throws IOException {
        tcpClient = new TCPClient(host,port);
    }

    /**
     * Demande à l'utilisateur de saisir les phrases
     */
    public void run() {
        super.askUserInput();
    }

    @Override
    public void onUserInput(String input) {
        tcpClient.sendRequest(input);

        System.out.println(tcpClient.readResponse());
    }

    @Override
    public void onUserLeave() {
        System.out.println("Déjà ... ?");
        tcpClient.stop();
    }

    public static void main(String args[]) {
        try {
            AsTCPClient clientManager = new AsTCPClient("localhost", Config.getAsServerPort());
            clientManager.run();
        } catch (IOException e) {
            System.out.println("ERREUR: " + e.getMessage());
        }
    }
}
