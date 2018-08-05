package ticket.com.utility;

public enum TYPE {
    START,              //start Game
    ALLCHAT,            //get the whole chat log
    NEWCHAT,            //get just the newest chat
    STATSUPDATE,
    HISTORYUPDATE,      //get newest history
    ALLHISTORY,         //get the whole history log
    BANKUPDATE,         //face up cards
    NEWROUTE,           //claim route on map
    NEW_DESTINATION_CARD, //assets view
    NEWTRAINCARD,
    NEWTEMPDECK,        //destination cards that the player can choose from
    DISCARDDESTINATION, //discard a destination card
    PLAYER_ADDED,
    TURN_NUMBER_CHANGED,
    MAP_DREW_TRAINCARD, //when you draw from the deck //meant to display message on drawing players screen
    ROUTECLAIMED,
    REMOVED_PLAYER,
    GAME_OVER,
    NEW_GAME_ADDED;
}