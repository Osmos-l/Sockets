package server;

import enums.TypeResponse;
import models.Request;
import models.RequestHandler;
import models.UDPServer;
import utils.Logger;

import java.net.DatagramPacket;

/**
 * Serveur UDP d'authentification
 */
public class AsUDPServer extends UDPServer {

    /**
     * Permet de gérer les requêtes que le serveur reçoit
     */
    private RequestHandler reqHandler;

    /**
     * État initial du serveur d'authentification UDP
     * @param port Sur lequel doit être lancé le serveur
     */
    public AsUDPServer(int port) {
        super(port);
        reqHandler = new RequestHandler();
    }

    @Override
    public String onRequest(DatagramPacket receiver, String input) {
        System.out.println("Request : " + input);
        String output;
        try {
            Request req = new Request(input);
            output = reqHandler.setRequest(req)
                    .execute();

            Logger.log(receiver.getAddress().getHostAddress(), receiver.getPort(), "UDP",
                    req.getCommand().toString(), req.getLogin(), output);
        } catch (Exception e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }

        return output;
    }

}
