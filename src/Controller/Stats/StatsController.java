package Controller.Stats;

import DAO.Reservation.OrdersDAOImpl;
import view.Stats.StatsView;

import java.util.Map;

/**
 * Reporting controller pour faire le lien avec la vue (pas de modèle nécessaire ici)
 */
public class StatsController {
    private final OrdersDAOImpl ordersDAO;

    public StatsController(OrdersDAOImpl ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    /**
     * Affiche trois vues :
     * - la répartition des paiements (payé vs pending)
     * - les revenus par attraction
     * - la popularité des attractions (nombre de commandes)
     */
    public void displayStats() {
        Map<String, Integer> pieData = ordersDAO.getStatusCount();
        Map<String, Float> revenueData = ordersDAO.getRevenueByAttraction();
        Map<String, Integer> popularityData = ordersDAO.getOrderCountByAttraction(); // 🆕

        new StatsView(pieData, revenueData, popularityData);
    }
}
