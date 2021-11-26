package server;

import enums.TypeResponse;
import models.Request;
import models.RequestHandler;
import utils.JsonLogger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Server {

    private DatagramSocket socket;

    private byte[] tampon;

    private DatagramPacket receiver;

    public UDPServer(int port) throws IOException {
        super("UPD", port);
    }

    @Override
    public void startServer() throws IOException {
        init();
        live();
    }

    @Override
    public void init() throws SocketException {
        tampon = new byte[1024];
        socket = new DatagramSocket(getPort());
        receiver = new DatagramPacket(tampon, tampon.length);
    }

    private void live() throws IOException {
        while (true) {
            waitUntilInput();
        }
    }

    private void waitUntilInput() throws IOException {
        socket.receive(receiver);

        String input = new String(receiver.getData(), 0, receiver.getLength());
        System.out.println("Request : " + input);

        handleInput(input);
    }

    private void handleInput(String input) throws IOException {
        String output;
        try {
            Request req = new Request(input);
            getReqHandler().setRequest(req);
            output = getReqHandler().execute();

            JsonLogger.log(getHost(), getPort(), getProtocole(), req.getCommand().toString(), req.getLogin(),
                    req.getPassword());
        } catch (Exception e) {
            output = TypeResponse.ERROR.getMessage() + " " + e.getMessage();
        }

        sendOutput(output);
    }

    private void sendOutput(String output) throws IOException {
        System.out.println("Response : " + output);

        DatagramPacket response = new DatagramPacket(output.getBytes(), output.getBytes().length,
                receiver.getAddress(), receiver.getPort());

        socket.send(response);

        clearReceiver();
    }

    private void clearReceiver() {
        receiver.setLength(tampon.length);
    }
}
