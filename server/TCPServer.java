package server;

import enums.TypeResponse;
import models.Request;
import models.RequestHandler;
import utils.JsonLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Authenticator;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Server {

    private ServerSocket serverSocket;

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
        serverSocket = new ServerSocket(getPort());
    }

    private void listen() throws IOException {
        while(true) {
            Socket connection = serverSocket.accept();

            BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintStream sortieSocket = new PrintStream(connection.getOutputStream());

            String input = "";
            while (input != null) {
                input = entreeSocket.readLine();

                if (input != null) {
                    String output = handleInput(input);
                    sortieSocket.println(output);
                }
            }

            connection.close();
        }
    }
}
