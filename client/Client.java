package client;

import java.io.IOException;
import java.util.Scanner;

public abstract class Client {

    private final String host = "localhost";

    private final String breakpoint = "FIN";

    private int port;

    private String protocole;

    private Scanner scanner;

    public Client(String protocole, int port) {
        this.protocole = protocole;
        this.port = port;
    }

    public void init() throws IOException {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        init();
        live();
    }

    public void live() throws IOException {
        System.out.println("Tapez vos phrases ou "+ breakpoint +" pour arrêter :");
        String input = "";
        while (!input.equalsIgnoreCase(breakpoint)) {
            input = scanner.nextLine();
            handleInput(input);
        }

        afterLive();
    }

    public abstract void afterLive() throws IOException;

    public abstract void handleInput(String input) throws IOException;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

}
