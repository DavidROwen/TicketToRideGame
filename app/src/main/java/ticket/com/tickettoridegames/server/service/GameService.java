package ticket.com.tickettoridegames.server.service;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.Route;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.service.IGameService;
import ticket.com.tickettoridegames.utility.web.Command;

public class GameService implements IGameService {
    @Override
    public void initGame(String gameId) {
        ServerModel.getInstance().getGames().get(gameId).initGame();
        Game game = ServerModel.getInstance().getGames().get(gameId);

        //send staring deck to players
        for(String playerId : game.getPlayers().keySet()){
             drawDestinationCard(playerId,gameId);
        }


        //turnOrder //because it's generated randomly
        List<String> turnOrder = game.getTurnOrder();
//        new GamePlayService().setTurnOrder(turnOrder);
        Command initTurnOrder = new Command(GamePlayService.class, new GamePlayService(),
                "setTurnOrder", new Object[]{turnOrder}
        );
        CommandsManager.addCommandAllPlayers(initTurnOrder, gameId);

        //playerColors //because it's generated randomly
        Map<String, Player.COLOR> colors = game.getPlayersColors();
//        new GamePlayService().setPlayersColors(colors);
        Command initColors = new Command(GamePlayService.class, new GamePlayService(),
                "setPlayersColors", new Object[]{colors}
        );
        CommandsManager.addCommandAllPlayers(initColors, gameId);

        //trainDeck //because it's generated randomly
        Stack<TrainCard> trainCardsDeck = game.getTrainCardsDeck();
//        new GamePlayService().setTrainCardsDeck(trainCardsDeck);
        Command setTrainCardsDeck = new Command(GamePlayService.class, new GamePlayService(),
                "setTrainCardsDeck", new Object[]{trainCardsDeck}
        );
        CommandsManager.addCommandAllPlayers(setTrainCardsDeck, gameId);

        //everything else
        ServerModel.getInstance().getGames().get(gameId).initGameNonRandom();
//        new GamePlayService().initiatingGameNonRandom();
        Command initHands = new Command(GamePlayService.class, new GamePlayService(),
                "initiatingGameNonRandom", new Object[]{}
        );
        CommandsManager.addCommandAllPlayers(initHands, gameId);
    }

    @Override
    public void drawTrainCard(String playerId, String gameId) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.drawTrainCard(playerId);

//        new GamePlayService().drawingTrainCard(playerId);
        Command addTrainCard = new Command(GamePlayService.class, new GamePlayService(),
                "drawingTrainCard", new Object[]{playerId}
        );
        CommandsManager.addCommandAllPlayers(addTrainCard, gameId);

    }

    @Override
    public void pickupTrainCard(String playerId, String gameId, Integer index) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.pickupTrainCard(playerId, index);

//        new GamePlayService().pickingUpTrainCard(playerId, index);
        Command pickCard = new Command(GamePlayService.class, new GamePlayService(),
                "pickingUpTrainCard", new Object[]{playerId, index}
        );
        CommandsManager.addCommandAllPlayers(pickCard, gameId);
    }

    //Destination Card Functions
    @Override
    public void drawDestinationCard(String playerId, String gameId) {
        //draw card
        List<DestinationCard> drawnCards = ServerModel.getInstance().drawADestinationCard(gameId);

        //set up command to return data
        Command tempDeck = null;
        try {
            tempDeck = new Command(GamePlayService.class, GamePlayService.class.newInstance(),
                    "setTempDeck", new Object[]{drawnCards});
        }
        catch(Exception e){
            e.printStackTrace();
        }
        CommandsManager.addCommand(tempDeck, playerId);
    }

    @Override
    public void claimDestinationCard(String playerId, String gameId, List<DestinationCard> cards){
        ServerModel sm = ServerModel.getInstance();
        sm.claimDestinationCards(playerId, gameId, cards);
        //add some kind of error checking with the above function?
        //send commands to the other games updating card numbers or cards.
        Command claimCommand = null;
        try {
            claimCommand = new Command(GamePlayService.class, GamePlayService.class.newInstance(),
                    "updateDestinationCards", new Object[]{playerId, cards});
        }
        catch (Exception e){
            e.printStackTrace();
        }
        CommandsManager.addCommandAllPlayers(claimCommand, gameId);
    }

    @Override
    public void returnDestinationCard(String gameId, List<DestinationCard> cards) {
        ServerModel.getInstance().addDestinationCard(gameId, cards);
        Command discardCards = null;
        try {
            discardCards = new Command(GamePlayService.class, GamePlayService.class.newInstance(),
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
    public void claimRoute(String gameId, String playerId, Route route) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        Boolean success = game.claimRoute(playerId, route);
        if(!success) { return; } //nothing changed

//        new GamePlayService().claimingRoute(playerId, route);
        Command claimRoute = new Command(GamePlayService.class, new GamePlayService(),
                "claimingRoute", new Object[]{playerId, route}
        );
        CommandsManager.addCommandAllPlayers(claimRoute, gameId);
    }

    @Override
    public void switchTurn(String gameId) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.switchTurn();

//        new GamePlayService().switchingTurn();
        Command claimRoute = new Command(GamePlayService.class, new GamePlayService(),
                "switchingTurn", null
        );
        CommandsManager.addCommandAllPlayers(claimRoute, gameId);
    }
}
