package ticket.com.tickettoridegames.client.presenter;

import ticket.com.tickettoridegames.client.view.IAssetsView;

public interface IAssetsPresenter {
    void drawFromBank(Integer stack);

    void pickupCard(Integer index);

    IAssetsView getAssetsView();
}
