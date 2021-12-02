package models.network.client;

import models.enums.TypeResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Client Logger
 */
public class TCPClient {

    private Socket socket;

    private BufferedReader entreeSocket;

    private PrintStream sortieSocket;

    /**
     * État initial de l'objet
     * @param host L'host
     * @param port Le port sur lequel on souhaite communiquer
     * @throws IOException
     */
    public TCPClient(String host, int port) throws IOException {
        socket = new Socket(host, port);

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
     * Close the connection
     */
    public void stop() {
        try {
            socket.close();

            entreeSocket.close();
            sortieSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
