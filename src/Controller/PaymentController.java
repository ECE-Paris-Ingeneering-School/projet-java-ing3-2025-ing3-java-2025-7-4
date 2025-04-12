package Controller;

import DAO.OrdersDAOImpl;
import Model.OrdersModel;
import com.mysql.cj.log.NullLogger;
import view.PaymentView;

// Contrôleur PaymentController.java
public class PaymentController {
    private final OrdersDAOImpl orderDAO;

    public PaymentController(OrdersDAOImpl orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void effectuerPaiement(int orderId) {
        OrdersModel orderToPay = orderDAO.getOrderById(orderId);
        if (orderToPay!= null) {
            new PaymentView(orderToPay, this); // appel simplifié
        } else {
            System.out.println("Commande introuvable.");
        }
    }

    public void traitementRetourPaiement(OrdersModel order, boolean paiementReussi) {
        String nouveauStatut = paiementReussi ? "Paid" : "Pending";
        boolean ok = orderDAO.updateOrderStatus(order.getOrderId(), nouveauStatut);
        if (ok) order.setStatus(nouveauStatut);
    }
}
