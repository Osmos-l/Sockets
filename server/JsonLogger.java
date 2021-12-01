package server;

import models.TCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Classe Singleton qui permet de logger des requêtes vers un serveur de log sur le port 3244 de la machine locale
 * 
 * @author torguet
 *
 */
public class JsonLogger extends TCPServer {
	
	// Attributs à compléter

	/**
	 * Constructeur à compléter
	 */
	private JsonLogger() {
		super(3244);
	}
	
	/**
	 * Transforme une requête en Json
	 * 
	 * @param host machine client
	 * @param port port sur la machine client
	 * @param proto protocole de transport utilisé
	 * @param type type de la requête
	 * @param login login utilisé
	 * @param result résultat de l'opération
	 * @return un objet Json correspondant à la requête
	 */
	private JsonObject reqToJson(String host, int port, String proto, String type, String login, String result) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("host", host)
		   	   .add("port", port)
		   	   .add("proto", proto)
			   .add("type", type)
			   .add("login", login)
			   .add("result", result)
			   .add("date", new Date().toString());

		return builder.build();
	}
	
	/**
	 *  singleton
	 */
	private static JsonLogger logger = null;
	
	/**
	 * récupération du logger qui est créé si nécessaire
	 * 
	 * @return le logger
	 */
	private static JsonLogger getLogger() {
		if (logger == null) {
			logger = new JsonLogger();
			logger.start();
		}
		return logger;
	}
	
	/**
	 * méthode pour logger
	 * 
	 * @param host machine client
	 * @param port port sur la machine client
	 * @param proto protocole de transport utilisé
	 * @param type type de la requête
	 * @param login login utilisé
	 * @param result résultat de l'opération
	 */
	public static void log(String host, int port, String proto, String type, String login, String result) {
		JsonLogger logger = getLogger();

		JsonObject log = logger.reqToJson(host, port, proto, type, login, result);

		try {
			Socket socket = new Socket("localhost", 3244);

			BufferedReader entreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream sortieSocket = new PrintStream(socket.getOutputStream());

			sortieSocket.println(log.toString());

			System.out.println("LOG Response:" + entreeSocket.readLine());

			socket.close();
		} catch (IOException e) {
			System.out.println("LOG Response:" + e.getMessage());
		}

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

}
