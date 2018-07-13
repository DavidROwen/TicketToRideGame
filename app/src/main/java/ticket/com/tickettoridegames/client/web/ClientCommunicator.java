package ticket.com.tickettoridegames.client.web;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Serializer;

public class ClientCommunicator {
	public static Object send(Command command, Type returnType) {
		SendTask sendTask = new SendTask();

		sendTask.setReturnType(returnType);
		sendTask.execute(command);

//        while(sendTask.getStatus() != AsyncTask.Status.FINISHED){} //infinite loop
//        while(!sendTask.getFinished()){} //infinite loop
//        return sendTask.getResult();

		try {
			return sendTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return null; //error
	}

	private static class SendTask extends AsyncTask<Command, Void, Object> {
		private Object result = null;
		private Boolean finished = false;

		private Type returnType = null;

		public Object getResult() {
			if(!finished) { return false; }

			return result;
		}

		public Boolean getFinished() {
			return finished;
		}

		public void setReturnType(Type returnType) {
			this.returnType = returnType;
		}

		@Override
		protected void onPostExecute(Object o) {
			result = o;
			finished = true;

			super.onPostExecute(o);
		}

		@Override
		protected Object doInBackground(Command... commands) {
			result = null;
			finished = false;

			HttpURLConnection connection = openConnection(GENERIC_DESIGNATOR);
			sendToServer(connection, commands[0]);
			Object result = receive(connection, returnType);
			connection.disconnect();

			return result;
		}
	}
//	//Commands
//	public static Object send(Command command, Type returnType) {
//		HttpURLConnection connection = openConnection(GENERIC_DESIGNATOR);
//		sendToServer(connection, command);
//		Object result = receive(connection, returnType);
//		connection.disconnect();
//
//		return result;
//	}

	private static final String GENERIC_DESIGNATOR = "/generic";
	private static final int SERVER_PORT_NUMBER = 8082;

	private static final String SERVER_HOST = "localhost";
	private static final String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT_NUMBER;
	private static final String HTTP_POST = "POST";

	//Constructors
	private static HttpURLConnection openConnection(String designator) {
		HttpURLConnection connection = null;

		try {
			URL url = new URL(URL_PREFIX + designator);
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

	private static Object receive(HttpURLConnection connection, Type returnType) {
		Object result = null;

		try {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if(connection.getContentLength() == 0) {
					System.out.println("Response body was empty");
				} else if(connection.getContentLength() == -1) { //-1 means the body was not empty but has an unknown about of information
					InputStreamReader serverInputReader = new InputStreamReader(connection.getInputStream());
					result = Serializer.fromInputJson(serverInputReader, returnType);
					serverInputReader.close();
				}
			} else {
				throw new Exception(String.format("http code %d",	connection.getResponseCode()));
			}
		} catch (Exception e) {
			System.out.println("Could not get response code: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
}
