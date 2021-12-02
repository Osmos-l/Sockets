package server;

import models.TCPServer;
import utils.Config;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TCPLogger extends TCPServer {

	/**
	 * État initial du Serveur de log
	 */
	public TCPLogger(int port) {
		super(port);
	}

	@Override
	public String onRequest(Socket connection, String request) {
		Path path = Paths.get("./test.txt");
		String output;
		try {
			request = request + "\n";
			Files.write(path, request.getBytes(StandardCharsets.UTF_8),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			output = "DONE";
		} catch (IOException e) {
			output = "ERROR: " + e.getMessage();
		}
		return output;
	}

	public static void main(String[] args) {
		Thread tcpLogger = new TCPLogger(Config.getTcpLoggerPort());
		tcpLogger.start();
	}
}
