package server;

import enums.TypeResponse;
import models.Request;
import models.RequestHandler;
import models.TCPServer;

import java.net.Socket;

public class AsTCPServer extends TCPServer {

    private RequestHandler reqHandler;

    public AsTCPServer(int port) {
        super(port);

        reqHandler = new RequestHandler();
    }

    @Override
    public String onRequest(Socket connection, String input) {
        String output;
        try {
            Request req = new Request(input);
            reqHandler.setRequest(req);
            output = reqHandler.execute();

            JsonLogger.log(connection.getInetAddress().getHostAddress(), connection.getPort(), "TCP",
                    req.getCommand().toString(), req.getLogin(), output);
        } catch (Exception e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }

        return output;
    }


}
