package ticket.com.tickettoridegames.utility;

public enum TYPE {
    START,              //start Game
    ALLCHAT,            //get the whole chat log
    NEWCHAT,            //get just the newest chat
    STATSUPDATE,
    HISTORYUPDATE,      //get newest history
    ALLHISTORY,         //get the whole history log
    BANKUPDATE,
    NEWROUTE,
    NEWTRAINCARD,
    NEWTEMPDECK,
    DESTINATIONUPDATE,  //Update a player owned destination card
    DISCARDDESTINATION; //discard a destination card
}
