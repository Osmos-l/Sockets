package models.buisness;

import models.enums.TypeResponse;
import models.network.server.TCPServer;
import models.utils.Logger;

import java.net.Socket;

/**
 * Serveur TCP d'authentification
 */
public class AsTCPServer extends TCPServer {

    /**
     * Permet de gérer les requêtes que le serveur reçoit
     */
    private RequestHandler reqHandler;

    /**
     * État initial du Serveur TCP d'authentification
     * @param port Le port du Serveur TCP
     */
    public AsTCPServer(int port) {
        super(port);

        reqHandler = new RequestHandler();
    }

    @Override
    public String onRequest(Socket connection, String input) {
        String output;
        try {
            Request req = new Request(input);
            output = reqHandler.setRequest(req)
                    .execute();

            Logger.log(connection.getInetAddress().getHostAddress(), connection.getPort(), "TCP",
                    req.getCommand().toString(), req.getLogin(), output);
        } catch (Exception e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }

        return output;
    }
}
