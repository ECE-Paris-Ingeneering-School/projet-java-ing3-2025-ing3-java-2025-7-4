
import Controller.PaymentController;
import Controller.ReportingController;
import DAO.DaoFactory;
import DAO.OrdersDAOImpl;
import Model.OrdersModel;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Connexion BDD via ta DaoFactory custom
        DaoFactory dao = DaoFactory.getInstance("attractions_db", "root", "");
        OrdersDAOImpl daoImpl = new OrdersDAOImpl(dao);

        // Création d’une nouvelle commande
        OrdersModel newOrder = new OrdersModel(
                20, // ID auto-généré
                LocalDateTime.now().plusDays(1),
                2, // personnes
                07.50f,
                "Paid",
                1, // id attraction
                42 // id réservation fictive
        );
        ReportingController controller = new ReportingController(daoImpl);
        controller.afficherReporting();

        // Insertion dans la base
        boolean created = daoImpl.createOrder(newOrder);

        // Si l’order a bien été créé, lancer le paiement
        if (created) {
            PaymentController controllerPayement = new PaymentController(daoImpl);
            controllerPayement.effectuerPaiement(newOrder.getOrderId()); // << Ton workflow
        } else {
            System.out.println("Erreur lors de la création de la commande.");
        }


    }
}
