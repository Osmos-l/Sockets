package server;

import java.io.IOException;

public class ASServer {

    private Thread tcp;

    private Thread udp;

    public ASServer(int port) throws IOException {
        tcp = new AsTCPServer(port);
        udp = new AsUDPServer(port);

        tcp.start();
        udp.start();

        while (tcp.isAlive() || udp.isAlive());
    }

    public static void main(String[] args) {
        try {
            ASServer server = new ASServer(40000);
        } catch(IOException e) {
            System.out.println("ERREUR:" + e.getMessage());
        }
    }
}
