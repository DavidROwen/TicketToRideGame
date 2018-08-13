package ticket.com.server.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.utility.db.IDbFactory;
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
		if(args.length >= 3) { //if they specified plugins
			if (!canRun(args)) {
				return;
			}

			//setup server
			serverPortNumber = Integer.valueOf(args[0]);

			//setup manager
			boolean loadedDescriptions = pluginManager.loadDescriptions(PLUGIN_DESC_PATH);
			if (!loadedDescriptions) {
				return;
			}

			//setup database
			if (!canUsePluginName(args[1])) {
				return;
			}
			IDbFactory dbFactory = pluginManager.createPlugin(args[1], Integer.valueOf(args[2]));
			DatabaseManager.getInstance().setDbFactory(dbFactory);
			DatabaseManager.getInstance().assignRefreshCount(Integer.valueOf(args[2]));

			if (args.length > 3) {
				DatabaseManager.getInstance().clearDatabase();
			}

			DatabaseManager.getInstance().createServerModel(); //updates from db
		}

		new ServerCommunicator().run();
	}

	private static boolean canRun(String[] args) {
		if(args.length < 3) {
			System.out.println(
					"ERROR: Program expects arguments for:\n "
							+ "port number (\"8082\" for default)\n"
							+ "plugin type (\"none\" fore default)\n"
							+ "plugin ratio (\"1\" for default)\n"
			);
			return false;
		}

		if(!canUsePort(args[0])
			|| !canUsePluginRatio(args[2])
			|| (args.length > 3 && !canUsePluginCommand(args[3]))
		) {
			System.out.println("ERROR: Can't run server because of incorrect arguments");
			return false;
		}
		return true;
	}

	private static boolean canUsePluginCommand(String command) {
		if(command.charAt(0) != '-') {
			System.out.println("ERROR: Start plugin command \"" + command + "\" s with -");
			return false;
		}
		if(!command.contains("wipe")) {
			System.out.println("ERROR: Invalid plugin command \"" + command + "\""
					+ "\n possible commands include : -wipe");
			return false;
		}

		return true;
	}

	private static boolean canUsePluginRatio(String ratioString) {
		//check that it's an integer
		for(Character each : ratioString.toCharArray()) {
			if(!Character.isDigit(each)) {
				System.out.println("ERROR: Plugin ratio \"" + ratioString + "\" must be an integer");
				return false;
			}
		}
		if(Integer.valueOf(ratioString) < 0) {
			System.out.println("ERROR: Plugin ratio \"" + ratioString + "\"  must be positive");
			return false;
		}

		return true;
	}

	private static boolean canUsePluginName(String name) {
		if(!pluginManager.canCreate(name)) {
			String possiblePluginNamesString = "";
			for(String each : pluginManager.getPossiblePluginNames()) {
				possiblePluginNamesString += " " + each;
			}

			System.out.println("ERROR: Invalid plugin name \"" + name
					+ "\" possible names include: " + possiblePluginNamesString);
			return false;
		}
		return true;
	}

	private static boolean canUsePort(String portString) {
		//check that it's an integer
		for(Character each : portString.toCharArray()) {
			if(!Character.isDigit(each)) {
				System.out.println("ERROR: Port \"" + portString + "\" must be an integer");
				return false;
			}
		}
		if(Integer.valueOf(portString) < 0) {
			System.out.println("ERROR: Port \"" + portString + "\" must be positive");
			return false;
		}

		return true;
	}



}
