package ticket.com.tickettoridegames.client.service;

import java.util.List;

import ticket.com.tickettoridegames.client.web.ServerProxy;
import ticket.com.tickettoridegames.server.service.GameService;
import ticket.com.tickettoridegames.utility.model.Chat;
import ticket.com.tickettoridegames.utility.model.DestinationCard;
import ticket.com.tickettoridegames.utility.service.IGameService;
import ticket.com.tickettoridegames.utility.web.Command;

public class GamePlayService implements IGameService {
    @Override
    public void initGame(String gameId) {
        try {
//            GameService.class.newInstance().initGame(gameId);
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "initGame", new Object[]{gameId}
            );
            ServerProxy.sendCommand(command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawTrainCard(String playerId, String gameId) {
        try {
//            GameService.class.newInstance().drawTrainCard(playerId, gameId);
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "drawTrainCard", new Object[]{playerId, gameId}
                    );
            ServerProxy.sendCommand(command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pickupTrainCard(String playerId, String gameId, Integer index) {
        try {
//            GameService.class.newInstance().pickupTrainCard(playerId, gameId, index);
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "pickupTrainCard", new Object[]{playerId, gameId, index}
            );
            ServerProxy.sendCommand(command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawDestinationCard(String playerId, String gameId) {
        try {
//            GameService.class.newInstance().drawDestinationCard(playerId, gameId);
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "drawDestinationCard", new Object[]{playerId, gameId}
            );
            ServerProxy.sendCommand(command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnDestinationCard(String gameId, List<DestinationCard> card) {
        try {
//            GameService.class.newInstance().returnDestinationCards(gameId, cards);
            Command command = new Command(GameService.class, GameService.class.newInstance(),
                    "returnDestinationCards", new Object[]{gameId, card}
            );
            ServerProxy.sendCommand(command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
