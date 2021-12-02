package models;

import enums.TypeResponse;
import utils.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Client Logger
 */
public class ClientLogger {

    /**
     * Singleton
     */
    private static ClientLogger clientLogger;

    private Socket socket;

    private BufferedReader entreeSocket;

    private PrintStream sortieSocket;

    private ClientLogger() throws IOException {
        socket = new Socket("localhost", Config.getTcpLoggerPort());

        entreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sortieSocket = new PrintStream(socket.getOutputStream());
    };

    /**
     * Send a request to TCP Logger
     * @param message
     */
    public void sendRequest(String message) {
        sortieSocket.println(message);
    }

    /**
     * Read the last response of TCP Logger
     * @return last response of TCP Logger
     */
    public String readResponse() {
        try {
            return entreeSocket.readLine();
        } catch (IOException e) {
            return TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }
    }

    /**
     * Permet de récupérer l'instance singleton de la classe
     * @return L'unique instance de la classe
     * @throws IOException
     */
    public static ClientLogger getInstance() throws IOException {
        if (clientLogger == null) {
            clientLogger = new ClientLogger();
        }
        return clientLogger;
    }

}
