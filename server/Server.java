package server;

import models.RequestHandler;

import java.io.IOException;

public abstract class Server extends Thread {

    private String host = "localhost";

    private String protocole;

    private int port;

    private RequestHandler reqHandler;

    public Server(String protocole, int port) {
        this.protocole = protocole;
        this.port = port;

        reqHandler = new RequestHandler();
    }

    public String getProtocole() {
        return protocole;
    }

    public int getPort() {
        return port;
    }

    public RequestHandler getReqHandler() {
        return this.reqHandler;
    }

    public String getHost() {
        return host;
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
