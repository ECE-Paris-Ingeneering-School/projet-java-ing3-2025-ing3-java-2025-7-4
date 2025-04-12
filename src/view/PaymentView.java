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
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JLabel labelInfos = new JLabel("Commande n°" + order.getOrderId() + " - statut : " + order.getStatus());
        JLabel labelPrix = new JLabel("Montant à payer : " + order.getPrice() + " €");
        JButton boutonPayer = new JButton("Payer");

        boutonPayer.addActionListener(e -> {
            boolean paiementOk = true; // tu peux ajouter vérification ou simulation
            controller.traitementRetourPaiement(order, paiementOk);
            JOptionPane.showMessageDialog(this, "Paiement " + (paiementOk ? "réussi !" : "annulé."));
            dispose();
        });

        add(labelInfos);
        add(labelPrix);
        add(boutonPayer);
        setVisible(true);
    }
}
