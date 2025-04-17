package Controller.Payement;

//Import des packages nécessaires

import DAO.Reservation.OrdersDAOImpl;
import Model.Reservation.OrdersModel;
import view.Reservation.PaymentView;



// Contrôleur PaymentController.java
public class PaymentController {
    //Attribut pour le DAO
    private final OrdersDAOImpl orderDAO;

    /**
     * Constructeur
     *
     * @param orderDAO Dao relié à l'ordre
     */
    public PaymentController(OrdersDAOImpl orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * Méthode pour effectuer un payement d'une commande déja présente dans la base de donnée
     *
     * @param orderId identifiant de la commande
     */
    public void effectuerPaiement(int orderId) {
        OrdersModel orderToPay = orderDAO.getOrderById(orderId);
        if (orderToPay != null) {
            new PaymentView(orderToPay); // appel simplifié
        } else {
            System.out.println("Commande introuvable.");
        }
    }

    /**
     * Méthode pour valider le payement (en fonction du nom et des chiffres sur la carte)
     *
     * @param nom         nom de l'utilisateur
     * @param nomCarte    nom sur la carte
     * @param numeroCarte numéro sur la carte
     * @return true ou false
     */
    public boolean validerPaiement(String nom, String nomCarte, String numeroCarte) {
        if (!nom.equalsIgnoreCase(nomCarte)) return false;
        return isLuhnValid(numeroCarte);
    }

    /**
     * Algorithme de Luhn permettant de vérifier que le numéro d'une carte bancaire est valide
     *
     * @param cardNumber numéro sur la carte
     * @return true ou false
     */
    private boolean isLuhnValid(String cardNumber) {
        //If spaces , the program will crash
        if (cardNumber == null || cardNumber.isBlank()) return false;
        cardNumber = cardNumber.replaceAll("\\s+", "");
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


    /**
     * Permet d'updater dans la base de donnée le status d'un paiement si il a bien été effectué ou non
     *
     * @param order          commande
     * @param paiementReussi indiquant si le payement a été validé ou non
     */
    public void traitementRetourPaiement(OrdersModel order, boolean paiementReussi) {
        String nouveauStatut = paiementReussi ? "Paid" : "Pending";
        boolean ok = orderDAO.updateOrderStatus(order.getOrderId(), nouveauStatut);
        if (ok) order.setStatus(nouveauStatut);
    }
}
