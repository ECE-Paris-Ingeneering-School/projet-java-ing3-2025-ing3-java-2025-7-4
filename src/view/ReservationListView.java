package view;

import DAO.DaoFactory;
import DAO.ReservationDAO;
import DAO.OrdersDAOImpl;
import Model.ReservationModel;
import Model.OrdersModel;
import toolbox.SessionManager;
import Model.ClientModel;

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

        model = new DefaultTableModel(new Object[]{
                "ID", "ClientID", "ProgrammeID", "Adultes", "Enfants", "Bébés", "Date", "Total", "Statut"
        }, 0);

        table = new JTable(model);
        loadReservations();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Supprimer la réservation");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int reservationId = (int) model.getValueAt(selectedRow, 0);
                boolean ok = reservationDAO.deleteReservationById(reservationId);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Réservation supprimée.");
                    loadReservations();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation.");
            }
        });

        JButton payButton = new JButton("Payer la réservation");
        payButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String status = (String) model.getValueAt(selectedRow, 8); // Colonne "Statut"
                if ("Paid".equalsIgnoreCase(status)) {
                    JOptionPane.showMessageDialog(this, "Cette réservation est déjà payée.");
                    return;
                }

                int reservationId = (int) model.getValueAt(selectedRow, 0);
                List<OrdersModel> commandes = ordersDAO.getOrdersByReservationId(reservationId);

                if (commandes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Aucune commande associée à cette réservation.");
                } else {
                    dispose();
                    new PaymentView(commandes.get(0));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation.");
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        bottomPanel.add(payButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadReservations() {
        model.setRowCount(0);
        ClientModel user = SessionManager.getCurrentUser();
        List<ReservationModel> reservations = reservationDAO.getAllReservations();

        for (ReservationModel r : reservations) {
            if (user.getAccountType() != 2 && r.getAccountId() != user.getId()) {
                continue; // Ignore les réservations qui ne sont pas à lui
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
}

