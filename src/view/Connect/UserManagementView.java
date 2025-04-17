package view.Connect;

import DAO.Client.ClientDAO;
import DAO.DaoFactory;
import Model.Client.ClientModel;
import toolbox.NavigationBar;
import view.Assets.FooterBar;

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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new NavigationBar("Gestion des clients"), BorderLayout.NORTH);

        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        clientDAO = new ClientDAO(daoFactory);

        model = new DefaultTableModel(new Object[]{"ID", "Nom complet", "Email", "Rôle","RoleCode"}, 0);
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
        JButton btnChangeRole = new JButton("Changer rôle");
        btnChangeRole.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) table.getValueAt(selectedRow, 0);
                int currentRole = (int) table.getValueAt(selectedRow, 4);

                if (currentRole == 0) {
                    JOptionPane.showMessageDialog(this, "Impossible de changer le rôle d'un invité !");
                    return;
                }

                boolean updated = clientDAO.toggleClientRole(id, currentRole);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "Rôle mis à jour avec succès !");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du rôle.");
                }
            }
        });


        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        bottomPanel.add(btnChangeRole);
        add(bottomPanel, BorderLayout.SOUTH);
        add(new FooterBar(), BorderLayout.SOUTH);

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
            model.addRow(new Object[]{c.getId(), c.getFullName(), c.getEmail(), roleLabel,c.getAccountType()});
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
