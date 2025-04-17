// ======================= ATTRACTION ADMIN VIEW =======================
package view.Attraction;

import Controller.Attraction.AttractionController;
import DAO.DaoFactory;
import Model.Attraction.AttractionModel;
import toolbox.NavigationBar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

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



        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Type", "Vidéo", "Image", "Prix"}, 0);
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

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);

        add(new NavigationBar("Gestion des Attractions"), BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

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
        JTextField nomField = new JTextField();
        JTextField descField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField videoField = new JTextField();
        JTextField imageField = new JTextField();
        JTextField prixField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom:")); panel.add(nomField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Type de public:")); panel.add(typeField);
        panel.add(new JLabel("Chemin vidéo:")); panel.add(videoField);
        panel.add(new JLabel("Chemin image:")); panel.add(imageField);
        panel.add(new JLabel("Prix:")); panel.add(prixField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter une attraction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            controller.createAttra(Map.of(
                    "nom", nomField.getText(),
                    "description", descField.getText(),
                    "typePers", typeField.getText(),
                    "video", videoField.getText(),
                    "image", imageField.getText(),
                    "prix", Float.parseFloat(prixField.getText())
            ));
            loadAttractions();
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
        String video = safeGetValue(row, 4);
        String image = safeGetValue(row, 5);
        String prixStr = safeGetValue(row, 6);

        JTextField nomField = new JTextField(nom);
        JTextField descField = new JTextField(desc);
        JTextField typeField = new JTextField(type);
        JTextField videoField = new JTextField(video);
        JTextField imageField = new JTextField(image);
        JTextField prixField = new JTextField(prixStr);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nom:")); panel.add(nomField);
        panel.add(new JLabel("Description:")); panel.add(descField);
        panel.add(new JLabel("Type de public:")); panel.add(typeField);
        panel.add(new JLabel("Chemin vidéo:")); panel.add(videoField);
        panel.add(new JLabel("Chemin image:")); panel.add(imageField);
        panel.add(new JLabel("Prix:")); panel.add(prixField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modifier l'attraction",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            controller.updateAttra(Map.of(
                    "id", tableModel.getValueAt(row, 0),
                    "nom", nomField.getText(),
                    "description", descField.getText(),
                    "typePers", typeField.getText(),
                    "video", videoField.getText(),
                    "image", imageField.getText(),
                    "prix", Float.parseFloat(prixField.getText())
            ));
            loadAttractions();
        }
    }

    private String safeGetValue(int row, int col) {
        Object val = tableModel.getValueAt(row, col);
        return val != null ? val.toString() : "";
    }
}
