package ticket.com.tickettoridegames.client.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
                    "claimDestinationCard", new Object[]{playerId, gameId, cards});
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
                "returnDestinationCard", new Object[]{gameId, cards}
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


    public static void getHand(String playerId, String gameId) {
        Command command = new Command(GAME_SERVICE_STRING, null,
                "getHand", new Object[]{playerId, gameId}
        );
        ServerProxy.sendCommand(command);
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
        ClientModel.get_instance().claimRoute(playerId, route, typeChoice);
    }

    //Destination Cards (Model) functions
    public static void setTempDeck(ArrayList<DestinationCard> tempDeck){
        //deserialized destination cards as linkedtreemap
        ArrayList<DestinationCard> temp = new ArrayList<>();
        for(int i = 0; i < tempDeck.size(); i++) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(tempDeck.get(i)).getAsJsonObject();
            DestinationCard card = gson.fromJson(obj, DestinationCard.class);

            temp.add(card);
        }

        ClientModel.get_instance().setMyPlayerTempDeck(temp);
    }

    public static void updateDestinationCards(String playerId, LinkedList<DestinationCard> cards){
        //deserialized destination cards as linkedtreemap
        ArrayList<DestinationCard> temp = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(cards.get(i)).getAsJsonObject();
            DestinationCard card = gson.fromJson(obj, DestinationCard.class);

            temp.add(card);
        }

        ClientModel.get_instance().updateDestinationCards(playerId, temp);
    }

    public static void discardDestinationCards(LinkedList<DestinationCard> cards){
        //deserialized destination cards as linkedtreemap
        ArrayList<DestinationCard> temp = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(cards.get(i)).getAsJsonObject();
            DestinationCard card = gson.fromJson(obj, DestinationCard.class);

            temp.add(card);
        }

        ClientModel.get_instance().discardDestinationCards(temp);
    }
    //END Destination Cards (Model) functions

    //Game History functions
    public static void addToHistory(PlayerAction history){
        ClientModel.get_instance().addGameHistory(history);
    }
    //END Game History functions

    public static void setTrainCardsDeck(Stack<TrainCard> trainCardsDeck) {
        //build array //in order
        TrainCard[] temp = new TrainCard[trainCardsDeck.size()];
        for(int i = temp.length-1; i >= 0; i--) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(trainCardsDeck.pop()).getAsJsonObject();
            TrainCard card = gson.fromJson(obj, TrainCard.class);

            temp[i] = card;
        }

        //build stack
        Stack<TrainCard> deck = new Stack<>();
        deck.addAll(Arrays.asList(temp));

        ClientModel.get_instance().setTrainCardsDeck(deck);
    }

    public static void switchingTurn(String gameId) {
        ClientModel.get_instance().changeTurn(gameId);
    }

    public static void resetBank(String gameId) { ClientModel.get_instance().resetBank(gameId);}

    public static void gettingHand(String playerId, LinkedList<TrainCard> hand) {
        List<TrainCard> clientHand = ClientModel.get_instance().getMyActiveGame().getPlayer(playerId).getTrainCards();

        TrainCard[] temp = new TrainCard[hand.size()];
        for(int i = temp.length-1; i >= 0; i--) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(hand.pop()).getAsJsonObject();
            TrainCard card = gson.fromJson(obj, TrainCard.class);

            temp[i] = card;
        }

        //build stack
        LinkedList<TrainCard> serverHand = new LinkedList<>();
        serverHand.addAll(Arrays.asList(temp));

        try {
            if(clientHand == null || serverHand == null) {
                throw new NullPointerException();
            }
            if (clientHand.size() != serverHand.size()) {
                throw new AssertionError();
            }
            for (int i = 0; i < clientHand.size(); i++) {
                if (clientHand.get(i) == serverHand.get(i)) {
                    throw new AssertionError();
                }
            }
        } catch (AssertionError e) {
            printHand(clientHand, true);
            printHand(serverHand, false);
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        System.out.println("Checked that client and server side have the same trainCards");
    }

    private static void printHand(List<TrainCard> hand, boolean client) {
        System.out.print(client ? "Client: " : "Server: ");
        for(TrainCard each : hand) {
            System.out.print(each.toString() + " ");
        }
        System.out.print(".\n");
    }
}
