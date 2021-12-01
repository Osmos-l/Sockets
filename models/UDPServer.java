package models;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class UDPServer extends Server {

    private DatagramSocket socket;

    private byte[] tampon;

    private DatagramPacket receiver;


    public UDPServer(int port) {
        super("UDP", port);
    }

    @Override
    public void startServer() throws IOException {
        init();

        listen();
    }

    @Override
    public void init() throws IOException {
        tampon = new byte[1024];
        socket = new DatagramSocket(getPort());
        receiver = new DatagramPacket(tampon, tampon.length);
    }

    private void listen() throws IOException {
        while (true) {
            socket.receive(receiver);

            String input = new String(receiver.getData(), 0, receiver.getLength());

            String output = onRequest(receiver, input);
            System.out.println("Response : " + output);

            DatagramPacket response = new DatagramPacket(output.getBytes(), output.getBytes().length,
                    receiver.getAddress(), receiver.getPort());

            socket.send(response);

            receiver.setLength(tampon.length);
        }
    }

    public abstract String onRequest(DatagramPacket receiver, String request);
}
