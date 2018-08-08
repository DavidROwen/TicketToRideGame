package ticket.com.server.server.DAO;

import ticket.com.utility.model.Game;

public interface IGameDAO {

    Boolean addGame(Game game);

    Game getGame(String gameId);

    Boolean updateGame(String gameId, Game game);
}
