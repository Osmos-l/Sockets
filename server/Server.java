package server;

import enums.TypeResponse;
import models.Request;
import models.RequestHandler;
import utils.JsonLogger;

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

    public String handleInput(String input) {
        String output;
        try {
            Request req = new Request(input);
            reqHandler.setRequest(req);
            output = reqHandler.execute();

            JsonLogger.log(host, port, protocole, req.getCommand().toString(), req.getLogin(),
                    req.getPassword());
        } catch (Exception e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }
        return output;
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
