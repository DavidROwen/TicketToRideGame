package ticket.com.server.server.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.server.server.CommandsManager;
import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.service.IGameService;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class GameService implements IGameService {
    public static final String GAME_PLAY_SERVICE_PATH = "/Users/aaron/Documents/AndroidStudioProjects/TicketToRideGame/app/src/main/java/ticket/com/tickettoridegames/client/service/GamePlayService.java"

    @Override
    public void initGame(String gameId) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.initGame();

        //turnOrder //because it's generated randomly
        List<String> turnOrder = game.getTurnOrder();
//        new GamePlayService().setTurnOrder(turnOrder);
        Command initTurnOrder = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "setTurnOrder", new Object[]{turnOrder}
        );
        CommandsManager.addCommandAllPlayers(initTurnOrder, gameId);

        //playerColors //because it's generated randomly
        Map<String, Player.COLOR> colors = game.getPlayersColors();
//        new GamePlayService().setPlayersColors(colors);
        Command initColors = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "setPlayersColors", new Object[]{colors}
        );
        CommandsManager.addCommandAllPlayers(initColors, gameId);

        //trainDeck //because it's generated randomly
        Stack<TrainCard> trainCardsDeck = game.getTrainCardsDeck();
//        new GamePlayService().setTrainCardsDeck(trainCardsDeck);
        Command setTrainCardsDeck = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "setTrainCardsDeck", new Object[]{trainCardsDeck}
        );
        CommandsManager.addCommandAllPlayers(setTrainCardsDeck, gameId);

        //everything else
        ServerModel.getInstance().getGames().get(gameId).initGameNonRandom();
//        new GamePlayService().initiatingGameNonRandom();
        Command initHands = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "initiatingGameNonRandom", new Object[]{}
        );
        CommandsManager.addCommandAllPlayers(initHands, gameId);

        //send staring deck to players
        for(String playerId : game.getPlayers().keySet()){
            drawDestinationCard(playerId,gameId);
        }
    }

    @Override
    public void drawTrainCard(String playerId, String gameId) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.drawTrainCard(playerId);

//        new GamePlayService().drawingTrainCard(playerId);
        Command addTrainCard = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "drawingTrainCard", new Object[]{playerId}
        );
        CommandsManager.addCommandAllPlayers(addTrainCard, gameId);

    }

    @Override
    public void pickupTrainCard(String playerId, String gameId, Integer index) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.pickupTrainCard(playerId, index);

//        new GamePlayService().pickingUpTrainCard(playerId, index);
        Command pickCard = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "pickingUpTrainCard", new Object[]{playerId, index}
        );
        CommandsManager.addCommandAllPlayers(pickCard, gameId);

        if (game.tooManyLocos()){
            game.resetTrainBank();
            //        new GamePlayService().resetBank(playerId, index);
            Command resetBank = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                    "resetBank", new Object[]{gameId}
            );
            CommandsManager.addCommandAllPlayers(resetBank, gameId);
        }
    }

    //Destination Card Functions
    @Override
    public void drawDestinationCard(String playerId, String gameId) {
        //draw card
        List<DestinationCard> drawnCards = ServerModel.getInstance().drawTemporaryDestinationCards(gameId);

        try {
            Command tempDeck = new Command(GAME_PLAY_SERVICE_PATH, GamePlayService.class.newInstance(),
                    "setTempDeck", new Object[]{drawnCards});
            CommandsManager.addCommand(tempDeck, playerId);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void claimDestinationCard(String playerId, String gameId, LinkedList<DestinationCard> cards){
        ServerModel sm = ServerModel.getInstance();

        //deserialized destination cards as linkedtreemap
        LinkedList<DestinationCard> temp = new LinkedList<>();
        for(int i = 0; i < cards.size(); i++) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(cards.get(i)).getAsJsonObject();
            DestinationCard card = gson.fromJson(obj, DestinationCard.class);

            temp.add(card);
        }

        sm.claimDestinationCards(playerId, gameId, temp);
        //add some kind of error checking with the above function?

        //send commands to the other games updating destination cards.
        Command claimCommand = null;
        try {
            claimCommand = new Command(GAME_PLAY_SERVICE_PATH, GamePlayService.class.newInstance(),
                    "updateDestinationCards", new Object[]{playerId, cards});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        CommandsManager.addCommandAllPlayers(claimCommand, gameId);
    }

    @Override
    public void returnDestinationCard(String gameId, LinkedList<DestinationCard> cards) {
        //deserialized destination cards as linkedtreemap
        LinkedList<DestinationCard> temp = new LinkedList<>();
        for(int i = 0; i < cards.size(); i++) {
            //convert from LinkedTreeMap
            Gson gson = new Gson();
            JsonObject obj = gson.toJsonTree(cards.get(i)).getAsJsonObject();
            DestinationCard card = gson.fromJson(obj, DestinationCard.class);

            temp.add(card);
        }
        ServerModel.getInstance().addDestinationCard(gameId, temp);


//        ServerModel.getInstance().addDestinationCard(gameId, cards);
        Command discardCards = null;
        try {
            discardCards = new Command(GAME_PLAY_SERVICE_PATH, GamePlayService.class.newInstance(),
                    "discardDestinationCards", new Object[]{cards}
            );
        }
        catch (Exception e){
            e.printStackTrace();
        }
        CommandsManager.addCommandAllPlayers(discardCards, gameId);
    }
    //End Destination Card Functions

    @Override
    public Result claimRoute(String gameId, String playerId, String route) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        Result result = game.claimRoute(playerId, route);

//        new GamePlayService().claimingRoute(playerId, route);
        Command claimRoute = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "claimingRoute", new Object[]{playerId, route}
        );
        CommandsManager.addCommandAllPlayers(claimRoute, gameId);

        return result;
    }

    @Override
    public void switchTurn(String gameId) {
        Game game = ServerModel.getInstance().getGameById(gameId);
        game.switchTurn();

//        new GamePlayService().switchingTurn();
        Command switchingTurn = new Command(GAME_PLAY_SERVICE_PATH, new GamePlayService(),
                "switchingTurn", new Object[]{gameId}
        );
        CommandsManager.addCommandAllPlayers(switchingTurn, gameId);
    }
}