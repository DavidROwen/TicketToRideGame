package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class UtilityService {
    public static final String TESTER_SERVICE_PATH = "ticket.com.server.server.service.Tester";

    private ClientModel clientModel;

    public UtilityService(){
        clientModel = ClientModel.get_instance();
    }

    public Result clearServer(){
        try {
            Result result = ServerProxy.sendCommand(
                    new Command(TESTER_SERVICE_PATH,
                            ticket.com.server.server.service.Tester.class.newInstance(),
                            "clear",
                            null)
            );
            if (result.isSuccess()) {
                // Parse/get the user from the response here.
                // result.message should be set as the userID from the server.
                clientModel.setUser(null);
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }
}
