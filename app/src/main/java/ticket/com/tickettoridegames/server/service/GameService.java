package ticket.com.tickettoridegames.server.service;

import java.util.List;
import java.util.Map;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.server.CommandsManager;
import ticket.com.tickettoridegames.server.model.ServerModel;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.TrainCard;
import ticket.com.tickettoridegames.utility.service.IGameService;
import ticket.com.tickettoridegames.utility.web.Command;

public class GameService implements IGameService {
    //todo could put commands in the setters

    @Override
    public void addChat(Chat chat, String gameId) {

    }

    @Override
    public void returnDestinationCard(String gameId, DestinationCard card) {
        ServerModel.getInstance().addDestinationCard(gameId, card);

//        ClientModel.get_instance().addDestinationCard(card);
        Command addDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
                "addDestinationCard", new Object[]{card}
        );
        CommandsManager.addCommandAllPlayers(addDestinationCard, gameId);
    }

    @Override
    public void initGame(String gameId) {
        ServerModel.getInstance().initGame(gameId);

        //turnOrder
        List<String> turnOrder = ServerModel.getInstance().getTurnOrder(gameId);
//        ClientModel.get_instance().setTurnOrder(turnOrder);
        Command initTurnOrder = new Command(ClientModel.class, ClientModel.get_instance(),
                "setTurnOrder", new Object[]{turnOrder}
        );
        CommandsManager.addCommandAllPlayers(initTurnOrder, gameId);


        //playerColors
        Map<String, Player.COLOR> colors = ServerModel.getInstance().getPlayerColors(gameId);
//        ClientModel.get_instance().setPlayersColors(colors);
        Command initColors = new Command(ClientModel.class, ClientModel.get_instance(),
                "setPlayersColors", new Object[]{colors}
        );
        CommandsManager.addCommandAllPlayers(initColors, gameId);


        //trainCards
        for(Player player : ServerModel.getInstance().getPlayers(gameId)) {
            for (TrainCard card : ServerModel.getInstance().getPlayerHand(player.getId(), gameId)) {
//                ClientModel.get_instance().addTrainCard(card, player.getId());
                Command addTrainCard = new Command(ClientModel.class, ClientModel.get_instance(),
                        "addTrainCard", new Object[]{card, player.getId()}
                        );
                CommandsManager.addCommandAllPlayers(addTrainCard, gameId);
            }
        }

        //destinationCards
        for(Player player : ServerModel.getInstance().getPlayers(gameId)) {
            for (DestinationCard card : ServerModel.getInstance().getPlayerDestinationCards(player.getId(), gameId)) {
//                ClientModel.get_instance().addDestinationCard(card, player.getId());
                Command addDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
                        "addDestinationCard", new Object[]{card, player.getId()}
                        );
                CommandsManager.addCommandAllPlayers(addDestinationCard, gameId);
            }
        }
    }

    @Override
    public void drawTrainCard(String playerId, String gameId) {
        //draw card
        TrainCard drawnCard = ServerModel.getInstance().drawATrainCard(gameId);
//        ClientModel.get_instance().addTrainCard(drawnCard, playerId);
        Command addTrainCard = new Command(ClientModel.class, ClientModel.get_instance(),
                "addTrainCard", new Object[]{drawnCard, playerId}
        );
        CommandsManager.addCommandAllPlayers(addTrainCard, gameId);

        //flip over the next one
        TrainCard nextCard = ServerModel.getInstance().getTopTrainCard(gameId);
//        ClientModel.get_instance().setTopTrainCard(nextCard);
        Command resetTop = new Command(ClientModel.class, ClientModel.get_instance(),
                "setTopTrainCard", new Object[]{nextCard}
        );
        CommandsManager.addCommandAllPlayers(resetTop, gameId);
    }

    @Override
    public void drawDestinationCard(String playerId, String gameId) {
        //draw card
        DestinationCard drawnCard = ServerModel.getInstance().drawADestinationCard(gameId);
//        ClientModel.get_instance().addDestinationCard(drawnCard, playerId);
        Command addDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
                "addDestinationCard", new Object[]{drawnCard, playerId}
        );
        CommandsManager.addCommandAllPlayers(addDestinationCard, gameId);

        //it's gone now
        ClientModel.get_instance().removeDestinationCard();
        Command removeDestinationCard = new Command(ClientModel.class, ClientModel.get_instance(),
                "removeDestinationCard", new Object[]{}
        );
        CommandsManager.addCommandAllPlayers(removeDestinationCard, gameId);
    }
}
