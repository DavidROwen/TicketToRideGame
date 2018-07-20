package ticket.com.tickettoridegames.client.presenter;

import java.util.Observable;
import java.util.Observer;

import ticket.com.tickettoridegames.client.model.ClientModel;
import ticket.com.tickettoridegames.client.service.GamePlayService;
import ticket.com.tickettoridegames.client.view.IStatsView;
import ticket.com.tickettoridegames.utility.TYPE;

public class StatsPresenter implements IStatsPresenter , Observer {

    private GamePlayService gamePlayService;
    private ClientModel clientModel;
    private IStatsView statsView;

    public StatsPresenter(IStatsView view){
        statsView = view;
        gamePlayService = new GamePlayService();
        clientModel = ClientModel.get_instance();
        clientModel.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object arg){
        clientModel = (ClientModel) observable;
        TYPE type = (TYPE) arg;
        switch(type){
            default:
                //Why you updated me?
        }
    }
}
