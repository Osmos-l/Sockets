package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    private int port;

    private Socket socket;

    private BufferedReader entreeSocket;

    private PrintStream sortieSocket;

    private Scanner scanner;

    public TCPClient(int port) throws IOException {
        this.port = port;

        launchServer();
    }

    private void initSocket() throws IOException {
        socket = new Socket("localhost", port);
    }

    private void initBuffer() throws IOException {
        entreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sortieSocket = new PrintStream(socket.getOutputStream());
    }

    private void initScanner() {
        scanner = new Scanner(System.in);
    }

    private void listen() throws IOException {

        String chaine = "";
        System.out.println("Tapez vos phrases ou FIN pour arrêter :");

        while (!chaine.equalsIgnoreCase("FIN")) {
            // lecture clavier
            chaine = scanner.nextLine();
            sortieSocket.println(chaine); // on envoie la chaine au serveur

            // lecture d'une chaine envoyée à travers la connexion socket
            chaine = entreeSocket.readLine();
            System.out.println("Chaine reçue : "+chaine);
        }

        // on ferme nous aussi la connexion
        socket.close();
    }

    private void launchServer() throws IOException {
        initSocket();

        initBuffer();

        initScanner();

        listen();
    }

    public static void main(String args[]) {
        try {
            TCPClient clientManager = new TCPClient(40000);
        } catch (IOException e) {
            System.out.println("ERREUR: " + e.getMessage());
        }
    }
}
