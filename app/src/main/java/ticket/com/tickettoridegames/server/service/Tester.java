package ticket.com.tickettoridegames.server.service;

import ticket.com.tickettoridegames.utility.web.Result;

public class Tester { //todo delete, just a temporary file
    public static Result add(Integer a, Integer b) {
        return new Result(true, String.valueOf(a+b), null);
    }
}
