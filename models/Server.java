package models;

import java.io.IOException;

public abstract class Server extends Thread {

    private String protocole;

    private int port;

    public Server(String protocole, int port) {
        this.protocole = protocole;
        this.port = port;
    }


    public int getPort() {
        return port;
    }

    public abstract void startServer() throws IOException;

    public abstract void init() throws IOException;

    @Override
    public void run() {
        System.out.println("Serveur " + protocole + " lancé sur le port: " + port);
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
