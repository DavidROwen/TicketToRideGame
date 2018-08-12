package ticket.com.tickettoridegames.client.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.PlayerAction;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

/**
 * Creates game play related commands to be send to the server.
 * Also receives game play related commands from the server
 *
 * @invariant All commands have a void return type because they are used to execute commands
 */
public class GamePlayService {
    public static final String GAME_SERVICE_STRING = "ticket.com.server.server.service.GameService";
    /**
     * Starts the game for everyone in a game lobby.
     *
     * @pre the game(tied the the gameId) has enough players to start
     * @pre gameId is of type String
     * @pre one player hit the start game button in the lobby
     * @post the game has been started on the server and on each client in the game
     * @param gameId a string id that corresponds to a game on the server
     */
    public static void initGame(String gameId) {
//            new GameService.initGame(gameId);
        Command command = new Command(GAME_SERVICE_STRING, null,
                "initGame", new Object[]{gameId}
        );
        ServerProxy.sendCommand(command);

    }

    public static void drawTrainCard(String playerId, String gameId) {
//            new GameService().drawTrainCard(playerId, gameId);
        Command command = new Command(GAME_SERVICE_STRING, null,
                "drawTrainCard", new Object[]{playerId, gameId}
                );
        ServerProxy.sendCommand(command);
    }

    public static void pickupTrainCard(String playerId, String gameId, Integer index) {
//            new GameService().pickupTrainCard(playerId, gameId, index);
        Command command = new Command(GAME_SERVICE_STRING, null,
                "pickupTrainCard", new Object[]{playerId, gameId, index}
        );
        ServerProxy.sendCommand(command);
    }

    //Destination Card (Command) commands
    public void drawDestinationCard(String playerId, String gameId) {
        try {
            Command command = new Command(GAME_SERVICE_STRING, null,
                    "drawDestinationCard", new Object[]{playerId, gameId}
            );
            ServerProxy.sendCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void claimDestinationCard(String playerId, String gameId, LinkedList<DestinationCard> cards){
        try{
            Command command = new Command(GAME_SERVICE_STRING, null,
                    "claimDestinationCard",
                    new Object[]{playerId, gameId, cards.toArray(new DestinationCard[cards.size()])});
            ServerProxy.sendCommand(command);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void returnDestinationCard(String gameId, LinkedList<DestinationCard> cards) {
//                    GamePlayService.class.newInstance().discardDestinationCards(cards);
//        new GameService().returnDestinationCard(gameId, cards);
        Command command = new Command(GAME_SERVICE_STRING, null,
                "returnDestinationCard",
                new Object[]{gameId, cards.toArray(new DestinationCard[cards.size()])}
        );
        ServerProxy.sendCommand(command);
    }
    //END Destination Card (Command) functions

    public static Result claimRoute(String gameId, String playerId, String route, TrainCard.TRAIN_TYPE typeChoice) {
//        new GameService().claimRoute(gameId, playerId, route);
        Command command = new Command(GAME_SERVICE_STRING, null,
                "claimRoute", new Object[]{gameId, playerId, route, typeChoice}
        );
        return ServerProxy.sendCommand(command);
    }

    public static void switchTurn(String gameId) {
        Command command = new Command(GAME_SERVICE_STRING, null,
                "switchTurn", new Object[]{gameId}
                );
        ServerProxy.sendCommand(command);
    }


    public static void checkHand(String playerId, String gameId) {
        Command command = new Command(GAME_SERVICE_STRING, null,
                "checkHand", new Object[]{playerId, gameId}
        );
        ServerProxy.sendCommand(command);
    }

    public static void checkTrainCardsDeck(String gameId) {
        Command command = new Command(GAME_SERVICE_STRING, null,
                "checkTrainCardsDeck", new Object[]{gameId}
        );
        ServerProxy.sendCommand(command);
    }

    public static void endGame(String gameId){
        Command command = new Command(GAME_SERVICE_STRING, null ,
                "endGame",
                new Object[]{gameId});
        ServerProxy.sendCommand(command);
    }

    public static void endGameNow(String gameId){
        ClientModel.get_instance().endGame(gameId);
    }

    public static void setTurnOrder(LinkedList<String> order) {
        ClientModel.get_instance().setTurnOrder(order);
    }

    public static void setPlayersColors(HashMap<String, Player.COLOR> colors) {
        ClientModel.get_instance().setPlayersColors(colors);
    }

    public static void initiatingGameNonRandom() {
        ClientModel.get_instance().initMyGameNonRandom();
    }

    public static void drawingTrainCard(String playerId) {
        ClientModel.get_instance().drawTrainCard(playerId);
    }

    public static void pickingUpTrainCard(String playerId, Integer index) {
        ClientModel.get_instance().pickupTrainCard(playerId, index);
    }

    public static void claimingRoute(String playerId, String route, TrainCard.TRAIN_TYPE typeChoice) {
       try {
           if (!ClientModel.get_instance().claimRoute(playerId, route, typeChoice).isSuccess()) {
               System.out.println(route + " was claimed on the server side but not on the client side");
           } //the command is only passed if it succeeded on the server side
       } catch(Exception e) {
           e.printStackTrace();
       }
    }

    //Destination Cards (Model) functions
    public static void setTempDeck(DestinationCard[] tempDeckArray){
        List<DestinationCard> tempDeck = new LinkedList<>();
        tempDeck.addAll(Arrays.asList(tempDeckArray)); //because asList is un mutable
        ClientModel.get_instance().setMyPlayerTempDeck(tempDeck);
    }

    public static void updateDestinationCards(String playerId, DestinationCard[] cardsArray){
        LinkedList<DestinationCard> cards = new LinkedList<>();
        cards.addAll(Arrays.asList(cardsArray)); //because asList is un mutable
        ClientModel.get_instance().updateDestinationCards(playerId, cards);
    }

    public static void discardDestinationCards(DestinationCard[] cards){
        ClientModel.get_instance().discardDestinationCards(new LinkedList<>(Arrays.asList(cards)));
    }
    //END Destination Cards (Model) functions

    //Game History functions
    public static void addToHistory(PlayerAction history){
        ClientModel.get_instance().addGameHistory(history);
    }
    //END Game History functions

    public static void setTrainCardsDeck(TrainCard[] trainCardsDeck) {
        Stack<TrainCard> deck = new Stack<>();
        deck.addAll(Arrays.asList(trainCardsDeck));
        ClientModel.get_instance().setTrainCardsDeck(deck);
    }

    public static void switchingTurn(String gameId) {
        ClientModel.get_instance().changeTurn(gameId);
    }

    public static void resetBank(String gameId) { ClientModel.get_instance().resetBank(gameId);}

    public static void checkingInitGame() {
        if(!ClientModel.get_instance().isInitialized()) {
            System.out.println("ERROR: game was not initialized correctly");
            throw new AssertionError();
        }
        checkTrainCardsDeck(ClientModel.get_instance().getMyActiveGame().getId());
        for(String each : ClientModel.get_instance().getMyActiveGame().getPlayersId()) {
            checkHand(each, ClientModel.get_instance().getMyActiveGame().getId());
        }
        System.out.println("Confirmed that game was initialized correctly");
    }

    public static void checkingHand(String playerId, TrainCard[] hand) {
        if(hand == null) {
            System.out.println("Cannot check server hand because it's null");
            return;
        }
        List<TrainCard> clientHand = ClientModel.get_instance().getMyActiveGame().getPlayer(playerId).getTrainCards();

        //build stack
        LinkedList<TrainCard> serverHand = new LinkedList<>();
        serverHand.addAll(Arrays.asList(hand));

        try {
            if (clientHand.size() != serverHand.size()) {
                 throw new AssertionError("Client hand size does not match server hand size");
            }
            for (int i = 0; i < clientHand.size(); i++) {
                if (serverHand.get(i) == null) {
                    throw new NullPointerException();
                } else if(!clientHand.get(i).equals(serverHand.get(i))) {
                    throw new AssertionError("Hands do not match");
                }
            }
            System.out.println("Confirmed that client and server have the same trainCards for "
                    + ClientModel.get_instance().getMyActiveGame().getPlayer(playerId));
        } catch (AssertionError e) {
            printHand(clientHand, true);
            printHand(serverHand, false);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR: server returned some null cards");
        }
    }

    public static void checkingTrainCardsDeck(TrainCard[] trainDeck) {
        if(trainDeck == null) {
            System.out.println("Cannot check server deck because it's null");
            return;
        }
        //build stack
        Stack<TrainCard> serverDeck = new Stack<>();
        serverDeck.addAll(Arrays.asList(trainDeck));

        Stack<TrainCard> clientDeck = ClientModel.get_instance().getMyActiveGame().getTrainCardsDeck();

        try {
            if (clientDeck.size() != serverDeck.size()) {
                throw new AssertionError();
            }
            for (int i = 0; i < clientDeck.size(); i++) {
                if(serverDeck.get(i) == null) {
                    throw new NullPointerException();
                }
                if (!clientDeck.get(i).equals(serverDeck.get(i))) {
                    throw new AssertionError();
                }
            }
            System.out.println("Confirmed that client and server have the same train cards deck");
        } catch (AssertionError e) {
            System.out.println("ERROR: client and server do not have the same train cards deck");
            printDeck(clientDeck, true);
            printDeck(serverDeck, false);
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("ERROR: server returned some null cards");
        }
    }

    private static void printDeck(List<TrainCard> deck, boolean client) {
        System.out.print(client ? "Client: " : "Server: ");
        for(TrainCard each : deck) {
            System.out.print(each.getType().toString().charAt(0) + " ");
        }
        System.out.print(".\n");
    }

    private static void printHand(List<TrainCard> hand, boolean client) {
        System.out.print(client ? "Client: " : "Server: ");
        for(TrainCard each : hand) {
            System.out.print(each.toString() + " ");
        }
        System.out.print(".\n");
    }
}
