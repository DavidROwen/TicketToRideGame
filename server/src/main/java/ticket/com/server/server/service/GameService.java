package ticket.com.server.server.service;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ticket.com.server.server.CommandsManager;
import ticket.com.server.server.DB.DatabaseManager;
import ticket.com.server.server.model.ServerModel;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Game;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Result;

public class GameService {
    public static final String GAME_PLAY_SERVICE_PATH = "ticket.com.tickettoridegames.client.service.GamePlayService";

    public static void initGame(String gameId) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.initGame();

        //update database
        Command dbCommand = new Command(ServerModel.class.getName(), null, "initGame", new Object[]{gameId});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);


        //turnOrder //because it's generated randomly
        List<String> turnOrder = game.getTurnOrder();
//        new GamePlayService().setTurnOrder(turnOrder);
        Command initTurnOrder = new Command(GAME_PLAY_SERVICE_PATH, null,
                "setTurnOrder", new Object[]{turnOrder}
        );
        CommandsManager.addCommandAllPlayers(initTurnOrder, gameId);

        //playerColors //because it's generated randomly
        Map<String, Player.COLOR> colors = game.getPlayersColors();
//        new GamePlayService().setPlayersColors(colors);
        Command initColors = new Command(GAME_PLAY_SERVICE_PATH, null,
                "setPlayersColors", new Object[]{colors}
        );
        CommandsManager.addCommandAllPlayers(initColors, gameId);

        //trainDeck //because it's generated randomly
        List<TrainCard> trainCardsDeck = new LinkedList<>();
        trainCardsDeck.addAll(game.getTrainCardsDeck()); //otherwise it's getting changed before transfer
//        new GamePlayService().setTrainCardsDeck(trainCardsDeck);
        Command setTrainCardsDeck = new Command(GAME_PLAY_SERVICE_PATH, null,
                "setTrainCardsDeck", new Object[]{trainCardsDeck.toArray(new TrainCard[trainCardsDeck.size()])}
        );
        CommandsManager.addCommandAllPlayers(setTrainCardsDeck, gameId);

        //everything else
        ServerModel.initGameNonRandom(gameId);

        //update database
        Command db2Command = new Command(ServerModel.class.getName(), null, "initGameNonRandom", new Object[]{gameId});
        DatabaseManager.getInstance().addCommand(db2Command, gameId);

//        new GamePlayService().initiatingGameNonRandom();
        Command initHands = new Command(GAME_PLAY_SERVICE_PATH, null,
                "initiatingGameNonRandom", new Object[]{}
        );
        CommandsManager.addCommandAllPlayers(initHands, gameId);

        Command checkInitGame = new Command(GAME_PLAY_SERVICE_PATH, null,
                "checkingInitGame", new Object[]{}
        );
        CommandsManager.addCommandAllPlayers(checkInitGame, gameId);

        //send starting deck to players
        for (String playerId : game.getPlayers().keySet()) {
            drawDestinationCard(playerId, gameId);
        }
    }

    public static void drawTrainCard(String playerId, String gameId) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.drawTrainCard(playerId);

        //update database
        Command dbCommand = new Command(ServerModel.class.getName(), null, "drawTrainCard", new Object[]{gameId, playerId});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

//        new GamePlayService().drawingTrainCard(playerId);
        Command addTrainCard = new Command(GAME_PLAY_SERVICE_PATH, null,
                "drawingTrainCard", new Object[]{playerId}
        );
        CommandsManager.addCommandAllPlayers(addTrainCard, gameId);

        //turns out that they randomly shuffle the same on both sides
    }

    public static void pickupTrainCard(String playerId, String gameId, Integer index) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.pickupTrainCard(playerId, index);

        //update database
        Command dbCommand = new Command(ServerModel.class.getName(), null, "pickupTrainCard", new Object[]{gameId, playerId, index});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

//        new GamePlayService().pickingUpTrainCard(playerId, index);
        Command pickCard = new Command(GAME_PLAY_SERVICE_PATH, null,
                "pickingUpTrainCard", new Object[]{playerId, index}
        );
        CommandsManager.addCommandAllPlayers(pickCard, gameId);

        if (game.tooManyLocos()) {
            game.resetTrainBank();

            //update database
            Command dbCommand2 = new Command(ServerModel.class.getName(), null, "resetTrainBank", new Object[]{gameId});
            DatabaseManager.getInstance().addCommand(dbCommand2, gameId);

            //        new GamePlayService().resetBank(playerId, index);
            Command resetBank = new Command(GAME_PLAY_SERVICE_PATH, null,
                    "resetBank", new Object[]{gameId}
            );
            CommandsManager.addCommandAllPlayers(resetBank, gameId);
        }
    }

    //Destination Card Functions
    public static void drawDestinationCard(String playerId, String gameId) {
        //draw card
        List<DestinationCard> drawnCards = ServerModel.drawTemporaryDestinationCards(gameId);

        //update database //ServerModel.drawTemporaryDestinationCards(gameId);
        Command dbCommand = new Command(ServerModel.class.getName(), null, "drawTemporaryDestinationCards", new Object[]{gameId});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

        try {
            Command tempDeck = new Command(GAME_PLAY_SERVICE_PATH, null,
                    "setTempDeck", new Object[]{drawnCards.toArray(new DestinationCard[drawnCards.size()])});
            CommandsManager.addCommand(tempDeck, playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void claimDestinationCard(String playerId, String gameId, DestinationCard[] cards) {
        ServerModel sm = ServerModel.getInstance();

        ServerModel.claimDestinationCards(playerId, gameId, cards);

        //update database //ServerModel.claimDestinationCards(playerId, gameId, temp);
        Command dbCommand = new Command(ServerModel.class.getName(), null, "claimDestinationCards",
                new Object[]{playerId, gameId, cards});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

        //send commands to the other games updating destination cards.
        Command claimCommand = null;
        try {
            claimCommand = new Command(GAME_PLAY_SERVICE_PATH, null,
                    "updateDestinationCards", new Object[]{playerId, cards});
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommandsManager.addCommandAllPlayers(claimCommand, gameId);
    }

    public static void returnDestinationCard(String gameId, DestinationCard[] cards) {
        ServerModel.getInstance().addDestinationCards(gameId, cards);

        //update database //ServerModel.addDestinationCards(gameId, temp);
        Command dbCommand = new Command(ServerModel.class.getName(), null, "addDestinationCards",
                new Object[]{gameId, cards});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

//        ServerModel.getInstance().addDestinationCards(gameId, cards);
        Command discardCards = null;
        try {
            discardCards = new Command(GAME_PLAY_SERVICE_PATH, null,
                    "discardDestinationCards", new Object[]{cards}
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommandsManager.addCommandAllPlayers(discardCards, gameId);
    }
    //End Destination Card Functions

    public static Result claimRoute(String gameId, String playerId, String route, TrainCard.TRAIN_TYPE typeChoice) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        Result result = game.claimRoute(playerId, route, typeChoice);

        //update database
        Command dbCommand = new Command(ServerModel.class.getName(), null, "claimRoute", new Object[]{gameId, playerId, route, typeChoice});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

        if (result.isSuccess()) {
//        new GamePlayService().claimingRoute(playerId, route);
            Command claimRoute = new Command(GAME_PLAY_SERVICE_PATH, null,
                    "claimingRoute", new Object[]{playerId, route, typeChoice}
            );
            CommandsManager.addCommandAllPlayers(claimRoute, gameId);
        }

        return result;
    }

    public static void switchTurn(String gameId) {
        Game game = ServerModel.getInstance().getGameById(gameId);
        game.switchTurn();

        //update database
        Command dbCommand = new Command(ServerModel.class.getName(), null, "switchTurn", new Object[]{gameId});
        DatabaseManager.getInstance().addCommand(dbCommand, gameId);

//        new GamePlayService().switchingTurn();
        Command switchingTurn = new Command(GAME_PLAY_SERVICE_PATH, null,
                "switchingTurn", new Object[]{gameId}
        );
        CommandsManager.addCommandAllPlayers(switchingTurn, gameId);
    }

    public static void checkHand(String playerId, String gameId) {
        List<TrainCard> hand = new LinkedList<>();
        hand.addAll(ServerModel.getInstance().getGameById(gameId).getPlayer(playerId).getTrainCards());

        Command gettingHandCommand = new Command(GAME_PLAY_SERVICE_PATH, null,
                "checkingHand", new Object[]{playerId, hand.toArray(new TrainCard[hand.size()])}
        );
        CommandsManager.addCommandAllPlayers(gettingHandCommand, gameId);
    }

    public static void checkTrainCardsDeck(String gameId) {
        Stack<TrainCard> deck = new Stack<>();
        deck.addAll(ServerModel.getInstance().getGameById(gameId).getTrainCardsDeck());

        Command command = new Command(GAME_PLAY_SERVICE_PATH, null,
                "checkingTrainCardsDeck", new Object[]{deck.toArray(new TrainCard[deck.size()])}
        );
        CommandsManager.addCommandAllPlayers(command, gameId);
    }

    public static void endGame(String gameId){
        ServerModel serverModel = ServerModel.getInstance();
        serverModel.endGame(gameId);

        //update database
        Command dbCommand2 = new Command(ServerModel.class.getName(), null, "endGame", new Object[]{gameId});
        DatabaseManager.getInstance().addCommand(dbCommand2, gameId);

        Command endGameCommand = new Command(GAME_PLAY_SERVICE_PATH, null,
                "endGameNow", new Object[]{gameId}
        );
        CommandsManager.addCommandAllPlayers(endGameCommand, gameId);
    }
}
