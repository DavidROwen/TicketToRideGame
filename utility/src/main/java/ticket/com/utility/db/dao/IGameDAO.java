package ticket.com.utility.db.dao;

import ticket.com.utility.model.Game;

public interface IGameDAO {

    Boolean addGame(Game game);

    Game getGame(String gameId);

    Boolean clearGames();

    Boolean updateGame(String gameId, Game game);
}
