package view;

import DAO.DaoFactory;
import DAO.ReservationDAO;
import Model.ClientModel;
import Model.ReservationModel;
import toolbox.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationListView extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ReservationDAO reservationDAO;

    public ReservationListView() {
        setTitle("Liste des réservations");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new NavigationBar("Liste des réservations"), BorderLayout.NORTH);

        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        reservationDAO = new ReservationDAO(daoFactory);

        model = new DefaultTableModel(new Object[]{
                "ID", "ClientID", "ProgrammeID", "Adultes", "Enfants", "Bébés", "Date", "Total"
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

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadReservations() {
        model.setRowCount(0);
        ClientModel user = SessionManager.getCurrentUser();
        List<ReservationModel> reservations;

        if (user != null && user.getAccountType() == 2) {
            // Admin : affiche toutes les réservations
            reservations = reservationDAO.getAllReservations();
        } else {
            // Utilisateur : affiche uniquement ses réservations
            reservations = reservationDAO.getReservationsByUserId(user.getId());
        }

        for (ReservationModel r : reservations) {
            model.addRow(new Object[]{
                    r.getReservationId(),
                    r.getAccountId(),
                    r.getProgramId(),
                    r.getAdultCount(),
                    r.getChildrenCount(),
                    r.getBabyCount(),
                    r.getDateReservation(),
                    r.getPrice()
            });
        }
    }
}
