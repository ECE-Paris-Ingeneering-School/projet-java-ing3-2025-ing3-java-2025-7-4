
import Controller.PaymentController;
import DAO.DaoFactory;
import DAO.OrdersDAOImpl;
import Model.OrdersModel;
import view.PaymentView;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Connexion BDD via ta DaoFactory custom
        DaoFactory dao = DaoFactory.getInstance("attractions_db", "root", "");
        OrdersDAOImpl daoImpl = new OrdersDAOImpl(dao);

        // Création d’une nouvelle commande
        OrdersModel newOrder = new OrdersModel(
                1, // ID auto-généré
                LocalDateTime.now().plusDays(1),
                2, // personnes
                35.50f,
                "Pending",
                1, // id attraction
                42 // id réservation fictive
        );

        // Insertion dans la base
        boolean created = daoImpl.createOrder(newOrder);

        // Si l’order a bien été créé, lancer le paiement
        if (created) {
            PaymentController controller = new PaymentController(daoImpl);
            controller.effectuerPaiement(newOrder.getOrderId()); // << Ton workflow
        } else {
            System.out.println("Erreur lors de la création de la commande.");
        }
    }
}
