package ticket.com.tickettoridegames.client.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.server.service.GameService;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.PlayerAction;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.service.IGameService;
import ticket.com.tickettoridegames.utility.web.Command;

public class GamePlayService implements IGameService {
    @Override
    public void initGame(String gameId) {
//            new GameService.initGame(gameId);
        Command command = new Command(GameService.class, new GameService(),
                "initGame", new Object[]{gameId}
        );
        ServerProxy.sendCommand(command);

    }

    @Override
    public void drawTrainCard(String playerId, String gameId) {
//            new GameService().drawTrainCard(playerId, gameId);
        Command command = new Command(GameService.class, new GameService(),
                "drawTrainCard", new Object[]{playerId, gameId}
                );
        ServerProxy.sendCommand(command);
    }

    @Override
    public void pickupTrainCard(String playerId, String gameId, Integer index) {
//            new GameService().pickupTrainCard(playerId, gameId, index);
        Command command = new Command(GameService.class, new GameService(),
                "pickupTrainCard", new Object[]{playerId, gameId, index}
        );
        ServerProxy.sendCommand(command);
    }

    //Destination Card (Command) commands
    @Override
    public void drawDestinationCard(String playerId, String gameId) {
        try {
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "drawDestinationCard", new Object[]{playerId, gameId}
            );
            ServerProxy.sendCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void claimDestinationCard(String playerId, String gameId, LinkedList<DestinationCard> cards){
        try{
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "claimDestinationCard", new Object[]{playerId, gameId, cards});
            ServerProxy.sendCommand(command);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void returnDestinationCard(String gameId, LinkedList<DestinationCard> cards) {
//                    GamePlayService.class.newInstance().discardDestinationCards(cards);
//        new GameService().returnDestinationCard(gameId, cards);
        Command command = new Command(GameService.class, new GameService(),
                "returnDestinationCard", new Object[]{gameId, cards}
        );
        ServerProxy.sendCommand(command);
    }
    //END Destination Card (Command) functions

    @Override
    public void claimRoute(String gameId, String playerId, Route route) {
//            new GameService().claimRoute(gameId, playerId, route);
        Command command = new Command(GameService.class, new GameService(),
                "claimRoute", new Object[]{gameId, playerId, route}
        );
        ServerProxy.sendCommand(command);
    }

    @Override
    public void switchTurn(String gameId) {
        // why are we calling client code? todo: QUESTION: why we are doing this?
        // new GameService().switchTurn(gameId);
        Command command = new Command(GameService.class, new GameService(),
                "switchTurn", new Object[]{gameId}
                );
        ServerProxy.sendCommand(command);
    }

    public void setTurnOrder(LinkedList<String> order) {
        ClientModel.get_instance().getMyActiveGame().setTurnOrder(order);
    }

    public void setPlayersColors(HashMap<String, Player.COLOR> colors) {
        ClientModel.get_instance().getMyActiveGame().setPlayersColors(colors);
    }

    public void initiatingGameNonRandom() {
        try {
            ClientModel.get_instance().getMyActiveGame().initGameNonRandom();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void drawingTrainCard(String playerId) {
        ClientModel.get_instance().getMyActiveGame().drawTrainCard(playerId);
    }

    public void pickingUpTrainCard(String playerId, Integer index) {
        ClientModel.get_instance().getMyActiveGame().pickupTrainCard(playerId, index);
    }

    public void claimingRoute(String playerId, Route route) {
        ClientModel.get_instance().getMyActiveGame().claimRoute(playerId, route);
    }

    //Destination Cards (Model) functions
    public void setTempDeck(ArrayList<DestinationCard> tempDeck){
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

    public void updateDestinationCards(String playerId, LinkedList<DestinationCard> cards){
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

    public void discardDestinationCards(LinkedList<DestinationCard> cards){
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
    public void addToHistory(PlayerAction history){
        ClientModel.get_instance().addGameHistory(history);
    }
    //END Game History functions

    public void setTrainCardsDeck(Stack<TrainCard> trainCardsDeck) {
        //build array //in order
        //todo loses the top 8 cards in serialization
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

        ClientModel.get_instance().getMyActiveGame().setTrainCardsDeck(deck);
    }

    public void switchingTurn() {
        ClientModel.get_instance().getMyActiveGame().switchTurn();
    }
}
