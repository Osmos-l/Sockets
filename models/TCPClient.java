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
public class TCPClient {

    private Socket socket;

    private BufferedReader entreeSocket;

    private PrintStream sortieSocket;

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

}
