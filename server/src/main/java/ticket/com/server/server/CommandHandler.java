package ticket.com.server.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Serializer;


public class CommandHandler implements HttpHandler {
    private static final Gson gson = new Gson(); //gson doesn't hold any state

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        //get input
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody());

        //read input
        Command command = (Command) Serializer.fromJson(inputStreamReader, Command.class);
        inputStreamReader.close();

        //process command
        Object obj = command.execute();

        //send output
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //!!!must go before output stream // 0 means the response body has an unknown amount of stuff in it

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(exchange.getResponseBody());
        Serializer.toJsonOutput(obj, outputStreamWriter);
        outputStreamWriter.close();

        exchange.close();
    }
}
