package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.web.Result;

public class Tester {
    public static Result add(Integer a, Integer b) {
        return new Result(true, String.valueOf(a+b), null);
    }

    // May need to be modified once we have a database
    public static Result clear(){
        ServerModel serverModel = ServerModel.getInstance();
        serverModel.clear();

        return new Result(true, "Server Model Cleared", null);
    }
}
