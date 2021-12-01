package models;

import models.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TCPServer extends Server {

    private ServerSocket socket;

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

    private void listen() throws IOException {
        while (true) {
            Socket connection = socket.accept();

            BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream sortieSocket = new PrintStream(connection.getOutputStream());

            String input = "";
            while (input != null) {
                input = entreeSocket.readLine();

                if (input != null) {
                    String output = onRequest(connection, input);

                    sortieSocket.println(output);
                }
            }

            connection.close();
        }
    }

    /**
     * Business logic
     * @param connection
     * @param request
     * @return Request response
     */
    public abstract String onRequest(Socket connection, String request);

}
