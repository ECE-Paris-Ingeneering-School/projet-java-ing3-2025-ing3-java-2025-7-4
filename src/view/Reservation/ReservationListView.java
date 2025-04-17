package view.Reservation;

import Controller.Payement.PaymentController;
import Controller.Reservation.ReservationListController;
import DAO.DaoFactory;
import DAO.Reservation.ReservationDAO;
import DAO.Reservation.OrdersDAOImpl;
import Model.Reservation.ReservationModel;
import Model.Reservation.OrdersModel;
import toolbox.SessionManager;
import Model.Client.ClientModel;
import toolbox.NavigationBar;
import view.Assets.FooterBar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationListView extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ReservationDAO reservationDAO;
    private OrdersDAOImpl ordersDAO;

    public ReservationListView() {

        setTitle("Liste des réservations");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new NavigationBar("Liste des réservations"), BorderLayout.NORTH);

        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        reservationDAO = new ReservationDAO(daoFactory);
        ordersDAO = new OrdersDAOImpl(daoFactory);

        model = new DefaultTableModel(new Object[]{"ID", "ClientID", "ProgrammeID", "Adultes", "Enfants", "Bébés", "Date", "Total", "Statut"}, 0);

        ReservationListController controller = new ReservationListController(this, reservationDAO, ordersDAO, model);

        table = new JTable(model);
        controller.loadReservations();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Supprimer la réservation");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int reservationId = (int) model.getValueAt(selectedRow, 0);
                controller.deleteReservation(reservationId);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation.");
            }
        });


        JButton payButton = new JButton("Payer la réservation");
        payButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String status = (String) model.getValueAt(selectedRow, 8);
                if ("Paid".equalsIgnoreCase(status)) {
                    JOptionPane.showMessageDialog(this, "Cette réservation est déjà payée.");
                    return;
                }

                int reservationId = (int) model.getValueAt(selectedRow, 0);
                controller.payerReservation(reservationId);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation.");
            }
        });


        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        bottomPanel.add(payButton);
        add(bottomPanel, BorderLayout.SOUTH);
        add(new FooterBar(), BorderLayout.SOUTH);

        setVisible(true);
    }
}

