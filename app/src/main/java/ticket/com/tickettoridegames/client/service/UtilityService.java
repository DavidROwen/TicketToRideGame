package ticket.com.tickettoridegames.client.service;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.utility.web.Command;
import ticket.com.tickettoridegames.utility.web.Result;

public class UtilityService {

    private ClientModel clientModel;

    public UtilityService(){
        clientModel = ClientModel.get_instance();
    }

    public Result clearServer(){
        try {
            Result result = ServerProxy.sendCommand(
                new Command(ticket.com.tickettoridegames.server.service.Tester.class,
                    ticket.com.tickettoridegames.server.service.Tester.class.newInstance(),
                    "clear",
                    null)
            );
            if (result.isSuccess()){
                // Parse/get the user from the response here.
                // result.message should be set as the userID from the server.
                clientModel.setUser(null);
                return result;
            }
            else {
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false, "", e.toString());
        }
    }
}
