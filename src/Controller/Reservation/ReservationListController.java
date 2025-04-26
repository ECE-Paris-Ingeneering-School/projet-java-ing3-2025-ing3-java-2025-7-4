package Controller.Reservation;

import Controller.Payement.PaymentController;
import DAO.Reservation.OrdersDAOImpl;
import DAO.Reservation.ReservationDAO;
import Model.Client.ClientModel;
import Model.Reservation.OrdersModel;
import Model.Reservation.ReservationModel;
import toolbox.SessionManager;
import view.Reservation.PaymentView;
import view.Reservation.ReservationListView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReservationListController {

    private final ReservationListView view;
    private final ReservationDAO reservationDAO;
    private final OrdersDAOImpl ordersDAO;
    private final DefaultTableModel model;

    public ReservationListController(ReservationListView view,
                                     ReservationDAO reservationDAO,
                                     OrdersDAOImpl ordersDAO,
                                     DefaultTableModel model) {
        this.view = view;
        this.reservationDAO = reservationDAO;
        this.ordersDAO = ordersDAO;
        this.model = model;
        loadReservations();
    }

    public void loadReservations() {
        model.setRowCount(0);
        ClientModel user = SessionManager.getCurrentUser();
        List<ReservationModel> reservations = reservationDAO.getAllReservations();

        for (ReservationModel r : reservations) {
            if (user.getAccountType() != 2 && r.getAccountId() != user.getId()) {
                continue;
            }

            float total = ordersDAO.getTotalPriceByReservationId(r.getReservationId());
            String status = ordersDAO.getStatusByReservationId(r.getReservationId());

            model.addRow(new Object[]{
                    r.getReservationId(),
                    r.getAccountId(),
                    r.getProgramId(),
                    r.getAdultCount(),
                    r.getChildrenCount(),
                    r.getBabyCount(),
                    r.getDateReservation(),
                    total,
                    status
            });
        }
    }

    public void deleteReservation(int reservationId) {
        boolean ok = reservationDAO.deleteReservationById(reservationId);
        if (ok) {
            JOptionPane.showMessageDialog(view, "Réservation supprimée.");
            loadReservations();
        } else {
            JOptionPane.showMessageDialog(view, "Erreur lors de la suppression.");
        }
    }

    public void payerReservation(ReservationModel reservation) {
        List<OrdersModel> commandes = ordersDAO.getOrdersByReservationId(reservation.getReservationId());
        if (commandes.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Aucune commande associée à cette réservation.");
        } else {
            PaymentController paymentController = new PaymentController(ordersDAO);
            PaymentView dialog = new PaymentView(null, commandes, reservation,paymentController);
            dialog.setVisible(true);
            view.dispose();
        }
    }

}
