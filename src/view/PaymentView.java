package view;

import Controller.PaymentController;
import Model.OrdersModel;
import javax.swing.*;
import java.awt.*;

public class PaymentView extends JFrame {
    public PaymentView(OrdersModel order, PaymentController controller) {
        setTitle("Paiement de la commande");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crée la barre de navigation
        NavigationBar nav = new NavigationBar("Legendaria", true,false, "ClientNom");

        // Crée le header avec infos de commande
        JLabel labelInfos = new JLabel("Commande n°" + order.getOrderId());
        JLabel labelStatut = new JLabel("Statut actuel : " + order.getStatus());
        JLabel labelPrix = new JLabel("Montant à payer : " + order.getPrice() + " €");

        JPanel header = new JPanel(new GridLayout(3, 1));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        header.add(labelInfos);
        header.add(labelStatut);
        header.add(labelPrix);

        // Combine nav + header dans un seul panneau vertical
        JPanel topWrapper = new JPanel();
        topWrapper.setLayout(new BoxLayout(topWrapper, BoxLayout.Y_AXIS));
        topWrapper.add(nav);
        topWrapper.add(header);

        add(topWrapper, BorderLayout.NORTH);

        // Formulaire de paiement
        JPanel contentPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField nomCarteField = new JTextField();
        JTextField numeroCarteField = new JTextField();
        JTextField cvcField = new JTextField();

        contentPanel.add(new JLabel("Nom :"));
        contentPanel.add(nomField);
        contentPanel.add(new JLabel("Email :"));
        contentPanel.add(emailField);
        contentPanel.add(new JLabel("Nom sur la carte :"));
        contentPanel.add(nomCarteField);
        contentPanel.add(new JLabel("Numéro de carte :"));
        contentPanel.add(numeroCarteField);
        contentPanel.add(new JLabel("CVC :"));
        contentPanel.add(cvcField);

        // Bouton valider
        JButton boutonPayer = new JButton("Valider le paiement");
        boutonPayer.addActionListener(e -> {
            String nom = nomField.getText();
            String nomCarte = nomCarteField.getText();
            String numeroCarte = numeroCarteField.getText();
            String cvc = cvcField.getText();

            boolean valid = controller.validerPaiement(nom, nomCarte, numeroCarte);
            controller.traitementRetourPaiement(order, valid);

            if (valid) {
                JOptionPane.showMessageDialog(this, "Paiement réussi ! Merci pour votre achat.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Paiement refusé. Veuillez vérifier vos informations.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel footer = new JPanel();
        footer.add(boutonPayer);

        add(contentPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}