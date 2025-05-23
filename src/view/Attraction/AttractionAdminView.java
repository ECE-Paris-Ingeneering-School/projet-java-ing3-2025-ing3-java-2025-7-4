//Package
package view.Attraction;

//Import nécessaire
import DAO.DaoFactory;
import Controller.Attraction.AttractionController;
import Model.Attraction.AttractionModel;
import toolbox.NavigationBar;
import view.Assets.FooterBar;

import java.util.Map;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AttractionAdminView extends JFrame {
    private AttractionController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public AttractionAdminView(int typeCompte) {
        if (typeCompte != 2) {
            JOptionPane.showMessageDialog(null, "Accès refusé : réservé aux administrateurs.");
            dispose();
            return;
        }

        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        controller = new AttractionController(daoFactory);


        setTitle("Gestion des Attractions - Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Barre de navigation
        NavigationBar nav = new NavigationBar("Gestion des Attractions");
        add(nav, BorderLayout.NORTH);



        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Type", "Image", "Prix"}, 0);
        table = new JTable(tableModel);
        loadAttractions();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Ajout des boutons d'action
        JButton addButton = new JButton("Ajouter une attraction");
        JButton editButton = new JButton("Modifier l'attraction sélectionnée");
        JButton deleteButton = new JButton("Supprimer l'attraction sélectionnée");

        addButton.addActionListener(e -> showAddDialog());
        editButton.addActionListener(e -> showEditDialog());
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int attractionId = (int) tableModel.getValueAt(selectedRow, 0);
                controller.deleteAttra(attractionId);
                loadAttractions();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction.");
            }
        });

        JPanel footerPanel = new JPanel(new BorderLayout());

        // Panel des boutons
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);

        // Ajout des boutons + footer ensemble
        footerPanel.add(bottomPanel, BorderLayout.CENTER);
        footerPanel.add(new FooterBar(), BorderLayout.SOUTH);

        add(footerPanel, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void loadAttractions() {
        tableModel.setRowCount(0);
        List<AttractionModel> attractions = controller.getAllAttractions();
        for (AttractionModel a : attractions) {
            tableModel.addRow(new Object[]{
                    a.getAttractionID(), a.getName(), a.getDescription(),
                    a.getTypePers(), a.getImage(), a.getPrix()
            });
        }
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField imageField = new JTextField();
        JTextField priceField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom:")); panel.add(nameField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Type de public:")); panel.add(typeField);
        panel.add(new JLabel("Chemin image:")); panel.add(imageField);
        panel.add(new JLabel("Prix:")); panel.add(priceField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter une attraction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double prix = Double.parseDouble(priceField.getText());

                controller.createAttra(Map.of(
                        "name", nameField.getText(),
                        "description", descField.getText(),
                        "person_type", typeField.getText(),
                        "image_path", imageField.getText(),
                        "price", prix
                ));

                loadAttractions();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Le prix doit être un nombre valide.");
            }
        }
    }


    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à modifier.");
            return;
        }

        String nom = safeGetValue(row, 1);
        String desc = safeGetValue(row, 2);
        String type = safeGetValue(row, 3);
        String image = safeGetValue(row, 4);
        String prixStr = safeGetValue(row, 5);

        JTextField nomField = new JTextField(nom);
        JTextField descField = new JTextField(desc);
        JTextField typeField = new JTextField(type);
        JTextField imageField = new JTextField(image);
        JTextField prixField = new JTextField(prixStr);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom:")); panel.add(nomField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Type de public:")); panel.add(typeField);
        panel.add(new JLabel("Chemin image:")); panel.add(imageField);
        panel.add(new JLabel("Prix:")); panel.add(prixField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modifier l'attraction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            controller.updateAttra(Map.of(
                    "attraction_id", tableModel.getValueAt(row, 0),
                    "name", nomField.getText(),
                    "description", descField.getText(),
                    "person_type", typeField.getText(),
                    "image_path", imageField.getText(),
                    "price", Double.parseDouble(prixField.getText())
            ));

            loadAttractions();
        }
    }

    private String safeGetValue(int row, int col) {
        Object val = tableModel.getValueAt(row, col);
        return val != null ? val.toString() : "";
    }
}
