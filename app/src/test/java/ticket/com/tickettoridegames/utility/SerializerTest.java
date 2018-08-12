package ticket.com.tickettoridegames.utility;

import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.LinkedList;

import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.utility.model.TrainCard;
import ticket.com.utility.web.Command;
import ticket.com.utility.web.Serializer;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    @Test
    public void toAndFromPrimitives() {
        Integer exp = 4;
        String json = Serializer.toJson(new Command(Integer.class.getName(), null, null, "valueOf", new Class<?>[]{int.class}, new Object[]{4}));
        Command command = (Command) Serializer.fromJson(json, Command.class);
        Integer act = (Integer) command.execute();

        assert exp.equals(act);
    }

    @Test
    public void testListParam() {
        LinkedList<TrainCard> cards = new LinkedList<>();
        cards.add(new TrainCard(TrainCard.TRAIN_TYPE.WILD));

        Class<?> type = new TypeToken<LinkedList<TrainCard>>(){}.getRawType();
//        GamePlayService.checkingHand("",cards);
        Command command = new Command(GamePlayService.class.getName(), null, null,
                    "checkingHand", new Class<?>[]{String.class, type}, new Object[]{"", cards});

        String cardsJson = Serializer.toJson(command);
        Command command2 = (Command) Serializer.fromJson(cardsJson, Command.class);

        LinkedList<TrainCard> cards2 = (LinkedList<TrainCard>) command2.getParamValues()[1];
        assertEquals(cards2.get(0).getType(), TrainCard.TRAIN_TYPE.WILD);
    }
}
