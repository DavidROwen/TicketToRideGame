package ticket.com.tickettoridegames.client.web;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Serializer;

public class ClientCommunicator {
	public static Object send(Command command, Type returnType) {
		SendTask sendTask = new SendTask();

		sendTask.execute(command);
		try {
			String input = sendTask.get();
			if(input == null) {
				System.out.println("server input: null");
				return null;
			} else {
				return Serializer.fromJson(input, returnType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null; //error
	}

	public static void setUrl(String url) {
		if(!url.isEmpty()) { urlPrefix = url; }
		else { urlPrefix = DEFAULT_URL_PREFIX; }
	}

	private static class SendTask extends AsyncTask<Command, Void, String> {
		@Override
		protected String doInBackground(Command... commands) {
			HttpURLConnection connection = openConnection(GENERIC_DESIGNATOR);
			sendToServer(connection, commands[0]);
			String input = receive(connection);

			connection.disconnect();
			return input;
		}
	}

	private static final String SERVER_HOST = "10.0.2.2";
	private static final int SERVER_PORT_NUMBER = 8082;
	private static final String DEFAULT_URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER;
	private static String urlPrefix = DEFAULT_URL_PREFIX;

	private static final String HTTP_POST = "POST";

	private static final String GENERIC_DESIGNATOR = "/generic";

	private static HttpURLConnection openConnection(String designator) {
		HttpURLConnection connection = null;

		try {
			URL url = new URL(urlPrefix + designator);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();

		} catch (MalformedURLException e) {
			System.out.println("Could not create url: " + e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			System.out.println("Could not open connection: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not connect: " + e.getMessage());
			e.printStackTrace();
		}

		return connection;
	}

	private static void sendToServer(HttpURLConnection connection, Object obj) {
		PrintWriter printWriter;

		try {
			printWriter = new PrintWriter(connection.getOutputStream());
			printWriter.print(Serializer.toJson(obj)); //send to server
			printWriter.close();

		} catch (IOException e) {
			System.out.println("Could not get output stream: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static String receive(HttpURLConnection connection) {
		try {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if(connection.getContentLength() == 0) {
					System.out.println("Response body was empty");
				} else if(connection.getContentLength() == -1) { //-1 means the body was not empty but has an unknown about of information
					InputStream inputStream = connection.getInputStream();
					String result = IOUtils.toString(inputStream, "UTF-8");
					inputStream.close();
					return result;
				}
			} else {
				throw new Exception(String.format("http code %d",	connection.getResponseCode()));
			}
		} catch (Exception e) {
			System.out.println("Could not get response code: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
}
