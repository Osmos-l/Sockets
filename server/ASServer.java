package server;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ASServer {

    public ASServer(int port) throws IOException {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new TCPServer(port));
        executor.execute(new UDPServer(port));

        while (!executor.isTerminated());
    }

    public static void main(String[] args) {
        try {
            ASServer server = new ASServer(40000);
        } catch(IOException e) {
            System.out.println("ERREUR:" + e.getMessage());
        }
    }
}
