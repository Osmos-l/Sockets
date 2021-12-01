package server;

import enums.TypeResponse;
import models.Request;
import models.RequestHandler;
import models.UDPServer;

import java.net.DatagramPacket;

public class AsUDPServer extends UDPServer {

    private RequestHandler reqHandler;

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
            reqHandler.setRequest(req);
            output = reqHandler.execute();

            JsonLogger.log(receiver.getAddress().getHostAddress(), receiver.getPort(), "UDP",
                    req.getCommand().toString(), req.getLogin(), output);
        } catch (Exception e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }

        return output;
    }

}
