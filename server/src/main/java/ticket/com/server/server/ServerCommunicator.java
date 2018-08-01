package ticket.com.server.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
	private static final Integer DEFAULT_SERVER_PORT_NUMBER = 8082;
	private static Integer serverPortNumber = DEFAULT_SERVER_PORT_NUMBER;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private static final String GENERIC_DESIGNATOR = "/generic";

	private static final CommandHandler COMMAND_HANDLER = new CommandHandler();

	private static HttpServer server;

	private ServerCommunicator() {}

	private void run() {
		try {
			server = HttpServer.create(new InetSocketAddress(serverPortNumber),
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
		if(args.length > 0) {
			try {
				serverPortNumber = Integer.valueOf(args[0]);
			} catch(NumberFormatException e){
				System.out.println(e + ". \"" + args[0] + "\" is not an integer");
			}
		}

		new ServerCommunicator().run();
	}

}
