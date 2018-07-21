package ticket.com.tickettoridegames.server.service;

import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Game;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.service.IGameService;
import ticket.com.tickettoridegames.utility.web.Command;

public class GameService implements IGameService {
    //todo could put commands in the setters

    @Override
    public void initGame(String gameId) {
        ServerModel.getInstance().initGame(gameId);

        Game game = ServerModel.getInstance().getGames().get(gameId);
        for(String playerId : game.getPlayers().keySet()){
            drawDestinationCard(playerId,gameId);
        }
        //turnOrder
        List<String> turnOrder = game.getTurnOrder();

//        ClientModel.get_instance().setTurnOrder(turnOrder);
        Command initTurnOrder = new Command(ClientModel.class, ClientModel.get_instance(),
                "setTurnOrder", new Object[]{turnOrder}
        );
        CommandsManager.addCommandAllPlayers(initTurnOrder, gameId);


//        //playerColors
//        Map<String, Player.COLOR> colors = ServerModel.getInstance().getPlayerColors(gameId);
////        ClientModel.get_instance().setPlayersColors(colors);
//        Command initColors = new Command(ClientModel.class, ClientModel.get_instance(),
//                "setPlayersColors", new Object[]{colors}
//        );
//        CommandsManager.addCommandAllPlayers(initColors, gameId);
//
//
//        //trainCards
//        for(Player player : ServerModel.getInstance().getPlayers(gameId)) {
//            for (TrainCard card : ServerModel.getInstance().getPlayerHand(player.getId(), gameId)) {
////                ClientModel.get_instance().addTrainCard(card, player.getId());
//                Command addTrainCard = new Command(ClientModel.class, ClientModel.get_instance(),
//                        "addTrainCard", new Object[]{card, player.getId()}
//                        );
//                CommandsManager.addCommandAllPlayers(addTrainCard, gameId);
//            }
//        }

//        //destinationCards
//        for(Player player : ServerModel.getInstance().getPlayers(gameId)) {
//            for (DestinationCard card : ServerModel.getInstance().getPlayerDestinationCards(player.getId(), gameId)) {
////                ClientModel.get_instance().addDestinationCard(card, player.getId());
//                Command addDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
//                        "addDestinationCard", new Object[]{card, player.getId()}
//                        );
//                CommandsManager.addCommandAllPlayers(addDestinationCard, gameId);
//            }
//        }
    }

    @Override
    public void drawTrainCard(String playerId, String gameId) {
        //server side
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.drawTrainCard(playerId);

        //client side
//        ClientModel.get_instance().getMyActiveGame().drawTrainCard(playerId);
        Command addTrainCard = new Command(Game.class, ClientModel.get_instance().getMyActiveGame(),
                "drawTrainCard", new Object[]{playerId}
        );
        CommandsManager.addCommandAllPlayers(addTrainCard, gameId);
    }

    @Override
    public void pickupTrainCard(String playerId, String gameId, Integer index) {
        Game game = ServerModel.getInstance().getGames().get(gameId);
        game.pickupTrainCard(playerId, index);

        //replaceCard
//        ClientModel.get_instance().getMyActiveGame().pickupTrainCard(playerId, index);
        Command resetTop = new Command(Game.class, ClientModel.get_instance().getMyActiveGame(),
                "pickupTrainCard", new Object[]{playerId, index}
        );
        CommandsManager.addCommandAllPlayers(resetTop, gameId);
    }

    @Override
    public List<DestinationCard> drawDestinationCard(String playerId, String gameId) {
        //draw card
        List<DestinationCard> drawnCard = ServerModel.getInstance().drawADestinationCard(gameId);
        return drawnCard;

        //send commands to update

//        Command addDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
//                "addDestinationCard", new Object[]{drawnCard, playerId}
//        );
//        CommandsManager.addCommandAllPlayers(addDestinationCard, gameId);
//
//        //it's gone now
//        ClientModel.get_instance().removeDestinationCard();
//        Command removeDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
//                "removeDestinationCard", new Object[]{}
//        );
//        CommandsManager.addCommandAllPlayers(removeDestinationCard, gameId);
    }

    public void claimDestinationCard(String playerId, String gameId, List<DestinationCard> cards){
        ServerModel.getInstance().claimDestinationCards(playerId, gameId, cards);
        //add some kind of error checking with the above function?
        //send commands to the other games updating card numbers or cards.
    }

    @Override
    public void returnDestinationCard(String gameId, List<DestinationCard> card) {
        ServerModel.getInstance().addDestinationCard(gameId, card);

//        ClientModel.get_instance().addDestinationCard(card);
        Command addDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
                "addDestinationCard", new Object[]{card}
        );
        CommandsManager.addCommandAllPlayers(addDestinationCard, gameId);
    }
}
