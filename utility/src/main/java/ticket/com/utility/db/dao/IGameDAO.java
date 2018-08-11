package ticket.com.utility.db.dao;

import java.util.List;

import ticket.com.utility.model.Game;

public interface IGameDAO {

    Boolean addGame(Game game);

    Game getGame(String gameId);

    List<Game> getAllGames();

    Boolean clearGames();

    Boolean updateGame(String gameId, Game game);
}
