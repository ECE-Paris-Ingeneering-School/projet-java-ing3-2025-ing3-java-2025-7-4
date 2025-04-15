package Controller;

//Import des packages nécessaires

import DAO.OrdersDAOImpl;
import view.ReportingView;

import java.util.Map;

/**
 * Reporting controller pour faire le lien avec la vue (pas de modèle nécessaire ici)
 */
public class ReportingController {
    /**
     * DAO
     */
    private final OrdersDAOImpl ordersDAO;

    /**
     * Constructueur
     *
     * @param ordersDAO dao associé
     */
    public ReportingController(OrdersDAOImpl ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    /**
     * Affiche deux vues
     * La répartition des payements payés et impayés et l'histogramme des revenus par attraction
     */
    public void afficherReporting() {
        Map<String, Integer> pieData = ordersDAO.getStatusCount();
        Map<String, Float> revenueData = ordersDAO.getRevenueByAttraction();
        new ReportingView(pieData, revenueData);
    }
}
