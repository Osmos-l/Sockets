package main.as;

import models.buisness.AsTCPServer;
import models.buisness.AsUDPServer;
import main.Config;

import java.io.IOException;

/**
 * Serveur d'authentification pour les protocoles TCP et UDP
 */
public class ASServer {

    /**
     * Serveur TCP
     */
    private Thread tcp;

    /**
     * Serveur UDP
     */
    private Thread udp;

    /**
     * État initial du serveur d'authentification
     * @param port Le port sur lequel sont lancés les serveurs TCP et UDP
     * @throws IOException
     */
    public ASServer(int port) throws IOException {
        tcp = new AsTCPServer(port);
        udp = new AsUDPServer(port);

        tcp.start();
        udp.start();

        while (tcp.isAlive() || udp.isAlive());
    }

    public static void main(String[] args) {
        try {
            ASServer server = new ASServer(Config.getAsServerPort());
        } catch (IOException e) {
            System.out.println("ERREUR:" + e.getMessage());
        }
    }
}
