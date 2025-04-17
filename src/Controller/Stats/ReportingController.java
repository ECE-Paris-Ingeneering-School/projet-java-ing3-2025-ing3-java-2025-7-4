package Controller.Stats;

import DAO.Reservation.OrdersDAOImpl;
import view.Stats.ReportingView;

import java.util.Map;

/**
 * Reporting controller pour faire le lien avec la vue (pas de modèle nécessaire ici)
 */
public class ReportingController {
    private final OrdersDAOImpl ordersDAO;

    public ReportingController(OrdersDAOImpl ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    /**
     * Affiche trois vues :
     * - la répartition des paiements (payé vs pending)
     * - les revenus par attraction
     * - la popularité des attractions (nombre de commandes)
     */
    public void afficherReporting() {
        Map<String, Integer> pieData = ordersDAO.getStatusCount();
        Map<String, Float> revenueData = ordersDAO.getRevenueByAttraction();
        Map<String, Integer> popularityData = ordersDAO.getOrderCountByAttraction(); // 🆕

        new ReportingView(pieData, revenueData, popularityData);
    }
}
