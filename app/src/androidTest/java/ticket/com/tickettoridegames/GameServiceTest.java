package ticket.com.tickettoridegames;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.service.JoinService;
import ticket.com.tickettoridegames.client.service.LobbyService;
import ticket.com.tickettoridegames.client.service.LoginService;
import ticket.com.tickettoridegames.client.service.UtilityService;
import ticket.com.tickettoridegames.client.web.Poller;
import ticket.com.utility.model.DestinationCard;
import ticket.com.utility.model.Player;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.model.User;
import ticket.com.utility.web.Result;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GameServiceTest {
    private String otherId;
    private String userId;
    private String gameId;

    private GamePlayService service;

    @Test
    public void testInitToGameplay() {
        initToGameplay();
        assertTrue(otherId != null && userId != null && gameId != null);
        assertTrue(ClientModel.get_instance().getMyActiveGame() != null);
        assertTrue(ClientModel.get_instance().getMyPlayer() != null);
    }

    @Test
    public void testInitGame() {
        initToGameplay();
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4){}

        assertEquals(ClientModel.get_instance().getMyActiveGame().getTurnOrder().size(), 2); //turn order
        assertEquals(ClientModel.get_instance().getMyActiveGame().getPlayersColors().size(), 2); //colors
        assertTrue(ClientModel.get_instance().getMyPlayer().getColor() != null);
//        assertTrue(ClientModel.get_instance().getMyActiveGame().getTrainCardsDeck().size() );
    }

    @Test
    public void testDrawTrainCard() {
        initToGameplay();

        //prepare
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4){} //initialized

        //test when you draw
        service.drawTrainCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() == 4){} //added here

        //test when others draw
        service.drawTrainCard(otherId, gameId);
        Map<String, Integer> countsOfCards;
        do {
            countsOfCards = ClientModel.get_instance().getMyActiveGame().getCountsOfCardsInHand();
        } while(countsOfCards.get(otherId) != 5); //added there
    }

    @Test
    public void testTrainBank() {
        initToGameplay();

        while(ClientModel.get_instance().getMyActiveGame().getTrainBank().size() != 5){} //initialized

        //prepare
        TrainCard prev0 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(0);
        TrainCard prev1 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(1);
        System.out.println(prev0.getType());

        //run
        service.pickupTrainCard(userId, gameId, 0);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 5){}
        while(ClientModel.get_instance().getMyActiveGame().getTrainBank().get(0) == prev0){}

        //check
        TrainCard cur1 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(1);
        assertEquals(prev1.getType(), cur1.getType()); //rest untouched
        assertEquals(ClientModel.get_instance().getMyPlayer().getTrainCards().get(4), prev0); //in hand

        TrainCard cur0 = ClientModel.get_instance().getMyActiveGame().getTrainBank().get(0);
        System.out.println("prev: " + prev0.getType() + " cur: " + cur0.getType()); //replaced
    }

    @Test
    public void testDestinationCards() {
        initToGameplay();

        service.drawDestinationCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTempDeck().size() != 3);
        List<DestinationCard> tempDeck = ClientModel.get_instance().getMyPlayer().getTempDeck();

        LinkedList<DestinationCard> claimed = new LinkedList<>();
        claimed.add(tempDeck.get(0));
        claimed.add(tempDeck.get(1));
        service.claimDestinationCard(userId, gameId, claimed);
        while(ClientModel.get_instance().getMyPlayer().getDestinationCards().size() != 2);

        LinkedList<DestinationCard> returning = new LinkedList<>();
        returning.add(tempDeck.get(2));
        Integer prevSize = ClientModel.get_instance().getMyActiveGame().getDestinationCards().size();
        service.returnDestinationCard(gameId, returning);
        while(ClientModel.get_instance().getMyActiveGame().getDestinationCards().size() == prevSize);
    }

    @Test
    public void testGetHand() {
        initToGameplay();

        service.checkHand(userId, gameId);

        service.drawTrainCard(userId, gameId);
        service.drawTrainCard(userId, gameId);
        service.drawTrainCard(userId, gameId);
        service.drawTrainCard(userId, gameId);

        service.checkHand(userId, gameId);
        service.drawTrainCard(userId, gameId);

        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4 + 4 + 1);
    }

    @Test
    public void testClaimRoute() {
        initToGameplay();

        //get cards //state changes in presenter level
        List<TrainCard> cards = ClientModel.get_instance().getMyPlayer().getTrainCards();
        while(!ClientModel.get_instance().getMyPlayer().canClaim(TrainCard.TRAIN_TYPE.BLUE, 2).isSuccess()) {
            int prevHandSize = cards.size();
            GamePlayService.drawTrainCard(userId, gameId);
            while (cards.size() == prevHandSize) ;
        }

        //claim
        GamePlayService.claimRoute(gameId, userId, "KC_saintLouis_first", TrainCard.TRAIN_TYPE.BLUE); //2 blue
        while(ClientModel.get_instance().getMyActiveGame().getClaimedRoutes().size() == 0);
    }

    @Test
    public void testCheckTrainDeck() {
        initToGameplay();

        //pass
        GamePlayService.checkTrainCardsDeck(gameId);
        //wait for poller
        GamePlayService.drawTrainCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4 + 1);

        //fail
        ClientModel.get_instance().getMyActiveGame().drawTrainCard(userId);
        GamePlayService.checkTrainCardsDeck(gameId);

        //wait for poller
        GamePlayService.drawTrainCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4 + 1 + 1);
    } //put breakpoint here to have the debugger wait for you to check "checkingTrainCardsDeck"

    @Test
    public void testMakeDiscardsToTrainDeck() {
        initToGameplay();
        Stack<TrainCard> deck = ClientModel.get_instance().getMyActiveGame().getTrainCardsDeck();
        List<TrainCard> discards = ClientModel.get_instance().getMyActiveGame().getTrainDiscards();

        //draw every card
        for(int i = 0; i < 110 - 4*2 - 5; i++) {
            GamePlayService.drawTrainCard(userId, gameId);
        }
        while(!deck.isEmpty());

        //add some cards to discard
        GamePlayService.claimRoute(gameId, userId, "calgary_winnipeg", TrainCard.TRAIN_TYPE.WHITE); //6 white
        GamePlayService.claimRoute(gameId, userId, "helena_denver", TrainCard.TRAIN_TYPE.GREEN); //4 green
        while(ClientModel.get_instance().getMyActiveGame().getClaimedRoutes().size() != 2);

        //draw a card from empty deck
        int prevDeckSize = deck.size();
        GamePlayService.drawTrainCard(userId, gameId);
        while(deck.size() == prevDeckSize);

        assertTrue(deck.size() == 6+4-1 || deck.size() == 6+4);//check that trainDeck is filled //depending on if the client has had time to draw
        assertTrue(discards.isEmpty());//check discards is cleared
        GamePlayService.checkTrainCardsDeck(gameId);//check that games are in sync
        //wait for poller
        int prevHandSize = ClientModel.get_instance().getMyPlayer().getTrainCards().size();
        GamePlayService.drawTrainCard(userId, gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != prevHandSize+1);
    }

    private void initToGameplay() {
        new UtilityService().clearServer();

        //login
        User otherPlayer = new User("username", "password");
        Result registerResult = new LoginService().register(otherPlayer);
        otherId = registerResult.getMessage();

        User user = new User("username2", "password2"); //register auto logs in //so it resets user
        Result registerResult1 = new LoginService().register(user);
        userId = registerResult1.getMessage();

        //poller
        Poller poller = new Poller(); //get it going

        //create game
        Result joinResult = JoinService.createGame(otherId, "gameName", 2);
        gameId = joinResult.getMessage();
        while(ClientModel.get_instance().getGames().isEmpty());

        //join game
        Player player1 = new Player(otherPlayer.getUsername(), otherPlayer.getId());
        JoinService.joinGame(otherId, gameId);

        Player player2 = new Player(user.getUsername(), user.getId());
        JoinService.joinGame(userId, gameId);
        while(ClientModel.get_instance().getMyActiveGame() == null);

        //start game
        LobbyService.startGame(gameId);
        while(!ClientModel.get_instance().isGameStarted(gameId)){}

        //init game
        service = new GamePlayService();
        service.initGame(gameId);
        while(ClientModel.get_instance().getMyPlayer().getTrainCards().size() != 4);
    }
}
