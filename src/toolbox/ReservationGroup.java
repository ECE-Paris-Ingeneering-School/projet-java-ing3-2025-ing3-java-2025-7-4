package toolbox;

import Model.OrdersModel;

import java.util.List;

public class ReservationGroup {

    private int accountId;
    private List<OrdersModel> commandes;
    private float total;

    public ReservationGroup(int accountId, List<OrdersModel> commandes) {
        this.accountId = accountId;
        this.commandes = commandes;
        this.total = calculerTotal(commandes);
    }

    private float calculerTotal(List<OrdersModel> commandes) {
        float somme = 0;
        for (OrdersModel o : commandes) {
            somme += o.getPrice();
        }
        return somme;
    }

    public int getAccountId() {
        return accountId;
    }

    public List<OrdersModel> getCommandes() {
        return commandes;
    }

    public float getTotal() {
        return total;
    }
}