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
    public boolean validerPaiement(String nom, String nomCarte, String numeroCarte, String cvc) {
        if (!nom.equalsIgnoreCase(nomCarte)) return false;
        return isValidCardNumber(numeroCarte);
    }

    private boolean isValidCardNumber(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public void traitementRetourPaiement(OrdersModel order, boolean paiementReussi) {
        String nouveauStatut = paiementReussi ? "Paid" : "Pending";
        boolean ok = orderDAO.updateOrderStatus(order.getOrderId(), nouveauStatut);
        if (ok) order.setStatus(nouveauStatut);
    }
}
