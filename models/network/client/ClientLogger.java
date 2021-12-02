package models.network.client;

import main.Config;

import java.io.IOException;

/**
 * Client Logger
 */
public class ClientLogger extends TCPClient {

    /**
     * Singleton
     */
    private static ClientLogger clientLogger;

    private ClientLogger() throws IOException {
        super("localhost", Config.getTcpLoggerPort());
    }

    /**
     * Permet de récupérer l'instance singleton de la classe
     * @return L'unique instance de la classe
     * @throws IOException
     */
    public static ClientLogger getInstance() throws IOException {
        if (clientLogger == null) {
            clientLogger = new ClientLogger();
        }
        return clientLogger;
    }

}
