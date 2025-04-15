package view;

import Controller.PaymentController;
import DAO.DaoFactory;
import DAO.OrdersDAOImpl;
import Model.OrdersModel;
import toolbox.SessionManager;
import toolbox.NavigationBarHelper;

import javax.swing.*;
import java.awt.*;

public class PaymentView extends JFrame {

    public PaymentView(OrdersModel order) {
        setTitle("Paiement de la commande");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barre de navigation
        NavigationBar nav = new NavigationBar("Legendaria");

        // Header commande
        JLabel labelInfos = new JLabel("Commande n°" + order.getOrderId());
        JLabel labelStatut = new JLabel("Statut actuel : " + order.getStatus());
        JLabel labelPrix = new JLabel("Montant à payer : " + order.getPrice() + " €");

        JPanel header = new JPanel(new GridLayout(3, 1));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        header.add(labelInfos);
        header.add(labelStatut);
        header.add(labelPrix);

        JPanel topWrapper = new JPanel();
        topWrapper.setLayout(new BoxLayout(topWrapper, BoxLayout.Y_AXIS));
        topWrapper.add(nav);
        topWrapper.add(header);

        add(topWrapper, BorderLayout.NORTH);

        // Formulaire
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

        // Bouton de paiement
        JButton boutonPayer = new JButton("Valider le paiement");
        boutonPayer.addActionListener(e -> {
            String nom = nomField.getText();
            String nomCarte = nomCarteField.getText();
            String numeroCarte = numeroCarteField.getText();

            PaymentController controller = new PaymentController(new OrdersDAOImpl(
                    DaoFactory.getInstance("attractions_db", "root", "")));

            boolean valid = controller.validerPaiement(nom, nomCarte, numeroCarte);
            controller.traitementRetourPaiement(order, valid);

            if (valid) {
                JOptionPane.showMessageDialog(this, "Paiement réussi ! Merci pour votre achat.");
                dispose();
                NavigationBarHelper.openAttractionView(null, SessionManager.getCurrentUser());
            } else {
                JOptionPane.showMessageDialog(this, "Paiement refusé. Veuillez vérifier vos informations.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
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
