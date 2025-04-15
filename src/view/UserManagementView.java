package view;

import DAO.ClientDAO;
import DAO.DaoFactory;
import Model.ClientModel;
import toolbox.NavigationBarHelper;
import toolbox.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementView extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ClientDAO clientDAO;

    public UserManagementView() {
        setTitle("Gestion des utilisateurs");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new NavigationBar("Gestion des clients"), BorderLayout.NORTH);

        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        clientDAO = new ClientDAO(daoFactory);

        model = new DefaultTableModel(new Object[]{"ID", "Nom complet", "Email", "Rôle"}, 0);
        table = new JTable(model);

        loadUsers();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Supprimer l'utilisateur sélectionné");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int clientId = (int) model.getValueAt(selectedRow, 0);
                deleteClient(clientId);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur.");
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void loadUsers() {
        model.setRowCount(0);
        List<ClientModel> clients = clientDAO.getAllClients();
        for (ClientModel c : clients) {
            String roleLabel = switch (c.getAccountType()) {
                case 2 -> "Admin";
                case 1 -> "Utilisateur";
                default -> "Invité";
            };
            model.addRow(new Object[]{c.getId(), c.getFullName(), c.getEmail(), roleLabel});
        }
    }

    public void deleteClient(int clientId) {
        if (clientDAO.deleteClientById(clientId)) {
            JOptionPane.showMessageDialog(this, "Client supprimé avec succès.");
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
