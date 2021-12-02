package client;

import models.ConsoleScanner;
import models.TCPClient;
import utils.Config;

import java.io.IOException;

public class AsTCPClient extends ConsoleScanner {

    private TCPClient tcpClient;

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

    public static void main(String args[]) {
        try {
            AsTCPClient clientManager = new AsTCPClient("localhost", Config.getAsServerPort());
            clientManager.run();
        } catch (IOException e) {
            System.out.println("ERREUR: " + e.getMessage());
        }
    }
}
