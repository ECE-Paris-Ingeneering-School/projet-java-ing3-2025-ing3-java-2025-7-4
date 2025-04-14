package Controller;

import DAO.OrdersDAOImpl;
import view.ReportingView;

import java.util.Map;

// --- ReportingController.java ---
public class ReportingController {
    private final OrdersDAOImpl ordersDAO;

    public ReportingController(OrdersDAOImpl ordersDAO) {
        this.ordersDAO = ordersDAO;
    }

    public void afficherReporting() {
        Map<String, Integer> pieData = ordersDAO.getStatusCount();
        Map<String, Float> revenueData = ordersDAO.getRevenueByAttraction();
        new ReportingView(pieData, revenueData);
    }
}
