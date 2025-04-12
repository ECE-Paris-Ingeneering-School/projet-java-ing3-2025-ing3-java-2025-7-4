package view;

// Vue PaymentView.java (simplifiée)
import Controller.PaymentController;
import Model.OrdersModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentView extends JFrame {
    public PaymentView(OrdersModel order, PaymentController controller) {
        setTitle("Paiement de la commande");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8, 1));

        JLabel labelInfos = new JLabel("Commande n°" + order.getOrderId() + " - statut : " + order.getStatus());
        JLabel labelPrix = new JLabel("Montant à payer : " + order.getPrice() + " €");

        JTextField nomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField nomCarteField = new JTextField();
        JTextField numeroCarteField = new JTextField();
        JTextField cvcField = new JTextField();

        JButton boutonPayer = new JButton("Payer");

        boutonPayer.addActionListener(e -> {
            String nom = nomField.getText();
            String nomCarte = nomCarteField.getText();
            String numeroCarte = numeroCarteField.getText();
            String cvc = cvcField.getText();

            boolean valid = controller.validerPaiement(nom, nomCarte, numeroCarte, cvc);
            controller.traitementRetourPaiement(order, valid);

            if (valid) {
                JOptionPane.showMessageDialog(this, "Paiement réussi !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Paiement refusé !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(labelInfos);
        add(labelPrix);
        add(new JLabel("Nom :"));
        add(nomField);
        add(new JLabel("Nom sur la carte :"));
        add(nomCarteField);
        add(new JLabel("Numéro de carte :"));
        add(numeroCarteField);
        add(new JLabel("CVC :"));
        add(cvcField);
        add(boutonPayer);

        setVisible(true);
    }
}
