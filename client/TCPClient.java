package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class TCPClient extends Client {

    private Socket socket;

    private BufferedReader entreeSocket;

    private PrintStream sortieSocket;

    public TCPClient(int port) throws IOException {
        super("TCP", port);

        start();
    }

    @Override
    public void init() throws IOException {
        super.init();

        socket = new Socket(getHost(), getPort());

        entreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sortieSocket = new PrintStream(socket.getOutputStream());
    }

    public void start() throws IOException {
        super.start();
        init();
    }

    private void sendInputToServer(String input) {
        sortieSocket.println(input);
    }

    private String getOutputFromServer() throws IOException {
        return entreeSocket.readLine();
    }

    public void handleInput(String input) throws IOException {
        sendInputToServer(input);

        String output = getOutputFromServer();
        System.out.println("Response : " + output);
    }

    @Override
    public void afterLive() throws IOException {
        socket.close();
    }

    public static void main(String args[]) {
        try {
            TCPClient clientManager = new TCPClient(40000);
        } catch (IOException e) {
            System.out.println("ERREUR: " + e.getMessage());
        }
    }
}
