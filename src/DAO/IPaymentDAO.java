package DAO;

import Model.PaymentModel;

import java.util.ArrayList;

/**
 * Interface IPaymentDAO
 * @author William
 * Cette interface définit les méthodes pour interagir avec la base de données concernant les paiements.
 * Elle permet de récupérer, sauvegarder, mettre à jour et supprimer des paiements.
 */
public interface IPaymentDAO {
    /**
     * Méthode pour récupérer tous les paiements
     * @return une liste de tous les paiements
     * @author William
     */
    ArrayList<PaymentModel> getAllPayments();

    /**
     * Méthode pour récupérer un paiement par son ID
     * @param id l'identifiant du paiement
     * @return le paiement correspondant à l'ID
     * @author William
     */
    PaymentModel getPayment(int id);

    /**
     * Méthode pour sauvegarder un nouveau paiement
     * @param payment le paiement à sauvegarder
     * @author William
     */
    void savePayment(PaymentModel payment);

    /**
     * Méthode pour mettre à jour un paiement existant
     * @param payment le paiement à mettre à jour
     * @author William
     */
    void updatePayment(PaymentModel payment);

    /**
     * Méthode pour supprimer un paiement par son ID
     * @param id l'identifiant du paiement à supprimer
     * @author William
     */
    void deletePayment(int id);
}
