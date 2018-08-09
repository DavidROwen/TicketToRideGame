package ticket.com.server.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.server.server.DB.IDbFactory;
import ticket.com.utility.web.Command;

public class ServerCommunicator {
	private static final Integer DEFAULT_SERVER_PORT_NUMBER = 8082;
	private static Integer serverPortNumber = DEFAULT_SERVER_PORT_NUMBER;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private static final String GENERIC_DESIGNATOR = "/generic";

	private static final CommandHandler COMMAND_HANDLER = new CommandHandler();

	private static HttpServer server;

	public static final String PLUGIN_DESC_PATH = "server/src/plugins/pluginDescriptions";
	private static PluginManager pluginManager = new PluginManager();

	private ServerCommunicator() { }

	private void run() {
		try {
			server = HttpServer.create(new InetSocketAddress(serverPortNumber),
					MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			System.out.println("ERROR: Could not create HTTP server: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		server.setExecutor(null); // use the default executor
		server.createContext(GENERIC_DESIGNATOR, COMMAND_HANDLER); //transfers input to handler

		server.start();
		System.out.println("Server Started on port: " + serverPortNumber);
	}

	public static void main(String[] args) {
		if(!canRun(args)) { return; }

		//setup server
		serverPortNumber = Integer.valueOf(args[0]);
//		new ServerCommunicator().run(); //todo no client to server communication

		//setup manager
		boolean loadedDescriptions = pluginManager.loadDescriptions(PLUGIN_DESC_PATH);
		if(!loadedDescriptions) { return; }

		//setup database
		if(!canUsePluginName(args[1])) { return; }
		IDbFactory dbFactory = pluginManager.createPlugin(args[1], Integer.valueOf(args[2]));
		DatabaseManager.getInstance().setDbFactory(dbFactory);

		if(args.length > 3) {
			DatabaseManager.getInstance().wipe();
		}

		DatabaseManager.getInstance().addCommand(new Command(null, (Object) null, null, null));
	}

	private static boolean canRun(String[] args) {
		if(args.length < 3) {
			throw new AssertionError(
					"ERROR: Program expects arguments for:\n "
							+ "port number (\"8082\" for default)\n"
							+ "plugin type (\"none\" fore default)\n"
							+ "plugin ratio (\"1\" for default)\n"
			);
		}

		if(!canUsePort(args[0])
			|| !canUsePluginRatio(args[2])
			|| (args.length > 3 && !canUsePluginCommand(args[3]))
		) {
			System.out.println("Can't run server because of incorrect arguments");
			return false;
		}
		return true;
	}

	private static boolean canUsePluginCommand(String command) {
		if(command.charAt(0) != '-') {
			throw new AssertionError("ERROR: Start plugin command \"" + command + "\" s with -");
		} else if(!command.contains("wipe")) {
			throw new AssertionError("ERROR: Invalid plugin command \"" + command + "\""
					+ "\n possible commands include : -wipe");
		}
		return true;
	}

	private static boolean canUsePluginRatio(String ratioString) {
		//check that it's an integer
		for(Character each : ratioString.toCharArray()) {
			if(!Character.isDigit(each)) {
				throw new AssertionError("ERROR: Plugin ratio \"" + ratioString + "\" must be an integer");
			}
		}
		if(Integer.valueOf(ratioString) < 0) {
			throw new AssertionError("ERROR: Plugin ratio \"" + ratioString + "\"  must be positive");
		}
		return true;
	}

	private static boolean canUsePluginName(String name) {
		if(!pluginManager.canCreate(name)) {
			String possiblePluginNamesString = "";
			for(String each : pluginManager.getPossiblePluginNames()) {
				possiblePluginNamesString += " " + each;
			}

			throw new AssertionError("ERROR: Invalid plugin name \"" + name
					+ "\" possible names include: " + possiblePluginNamesString);
		}
		return true;
	}

	private static boolean canUsePort(String portString) {
		//check that it's an integer
		for(Character each : portString.toCharArray()) {
			if(!Character.isDigit(each)) {
				throw new AssertionError("ERROR: Port \"" + portString + "\" must be an integer");
			}
		}
		if(Integer.valueOf(portString) < 0) {
			throw new AssertionError("ERROR: Port \"" + portString + "\" must be positive");
		}
		return true;
	}



}
