package models.network.server;

import models.network.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TCPServer extends Server {

    private ServerSocket socket;

    /**
     * État initial de l'objet
     * @param port Le port sur lequel tourne le serveur
     */
    public TCPServer(int port) {
        super("TCP", port);
    }

    @Override
    public void startServer() throws IOException {
        init();

        listen();
    }

    @Override
    public void init() throws IOException {
        socket = new ServerSocket(getPort());
    }

    /**
     * Coeur du serveur. Recupère, distribue et renvoie les requêtes
     * @throws IOException
     */
    private void listen() throws IOException {
        while (true) {
            Socket connection = socket.accept();

            BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream sortieSocket = new PrintStream(connection.getOutputStream());

            String input = "";
            while (input != null) {
                input = entreeSocket.readLine();

                if (input != null) {
                    System.out.println("Incoming request ...");
                    String output = onRequest(connection, input);
                    System.out.println(output + "\n");
                    sortieSocket.println(output);
                }
            }

            connection.close();
        }
    }

    /**
     * Business logic when incoming request
     * @param connection
     * @param request
     * @return String response
     */
    public abstract String onRequest(Socket connection, String request);

}
