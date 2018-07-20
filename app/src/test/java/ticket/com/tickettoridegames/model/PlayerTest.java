package ticket.com.tickettoridegames.model;

import org.junit.Test;

import ticket.com.tickettoridegames.utility.model.Player;
import ticket.com.tickettoridegames.utility.model.TrainCard;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {
    @Test
    public void testTrainCards() {
        Player player = new Player("username", "id");

        //test add
        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.GREEN));
        player.addTrainCard(new TrainCard(TrainCard.TRAIN_TYPE.BLUE));
        assertEquals((Integer) player.getTrainCards().size(), (Integer) 2);

        //test has and remove
        assertFalse(player.removeTrainCards(new TrainCard(TrainCard.TRAIN_TYPE.RED), new TrainCard(TrainCard.TRAIN_TYPE.GREEN)));
        assertTrue(player.removeTrainCards(new TrainCard(TrainCard.TRAIN_TYPE.BLUE), new TrainCard(TrainCard.TRAIN_TYPE.GREEN)));
    }
}
