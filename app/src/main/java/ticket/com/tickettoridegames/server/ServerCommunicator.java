package ticket.com.tickettoridegames.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
	private static final int SERVER_PORT_NUMBER = 8082;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	public static final String GENERIC_DESIGNATOR = "/generic";

	private static final CommandHandler COMMAND_HANDLER = new CommandHandler();

	private static HttpServer server;

	private ServerCommunicator() {}

	private void run() {
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			System.out.println("Could not create HTTP server: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		server.setExecutor(null); // use the default executor
		server.createContext(GENERIC_DESIGNATOR, COMMAND_HANDLER); //transfers input to handler

		server.start();
		System.out.println("Server Started" );
	}

	public static void main(String[] args) {
		new ServerCommunicator().run();
	}

}
