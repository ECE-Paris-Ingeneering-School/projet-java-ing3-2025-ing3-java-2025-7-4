package view.Reservation;

import Controller.Payement.PaymentController;
import DAO.DaoFactory;
import DAO.Reservation.OrdersDAOImpl;
import Model.Reservation.OrdersModel;
import Model.Reservation.ReservationModel;
import toolbox.SessionManager;
import toolbox.NavigationBarHelper;
import toolbox.NavigationBar;
import view.Assets.FooterBar;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class PaymentView extends JDialog {

    public PaymentView(JFrame parent, List<OrdersModel> commandes, ReservationModel reservation, PaymentController controller) {
        setTitle("Paiement de la commande");
        setSize(800, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        // Barre de navigation
        NavigationBar nav = new NavigationBar("Legendaria");

        // Calcul du prix total
        float totalPrix = 0;
        for (OrdersModel order : commandes) {
            totalPrix += order.getPrice();
        }

        // Header commandes
        JLabel labelInfos = new JLabel("Réservation n°" + reservation.getReservationId());
        JLabel labelStatut = new JLabel("Statut actuel : " + commandes.get(0).getStatus()); // on prend le statut de la 1ère commande
        JLabel labelPrix = new JLabel("Montant total à payer : " + totalPrix + " €");

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

        // Liste des commandes détaillées
        JPanel listeCommandes = new JPanel();
        listeCommandes.setLayout(new BoxLayout(listeCommandes, BoxLayout.Y_AXIS));
        listeCommandes.setBorder(BorderFactory.createTitledBorder("Détail des commandes :"));

        for (OrdersModel order : commandes) {
            String desc = (order.getAttractionId() == 0) ? "Entrée Parc" : "Attraction ID: " + order.getAttractionId();
            JLabel labelCommande = new JLabel(desc + " - " + order.getPrice() + " €");
            listeCommandes.add(labelCommande);
        }

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.add(listeCommandes, BorderLayout.NORTH);
        centerWrapper.add(contentPanel, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);

        // Bouton de paiement
        JButton boutonPayer = new JButton("Valider le paiement");
        boutonPayer.addActionListener(e -> {
            String nom = nomField.getText();
            String nomCarte = nomCarteField.getText();
            String numeroCarte = numeroCarteField.getText();

            boolean valid = controller.validerPaiement(nom, nomCarte, numeroCarte);

            // Ici, tu peux traiter tous les orders si besoin
            for (OrdersModel order : commandes) {
                controller.traitementRetourPaiement(order, valid);
            }

            if (valid) {
                JOptionPane.showMessageDialog(this, "Paiement réussi ! Merci pour votre achat.");
                dispose();
                controller.terminerPaiementAvecSucces(); // méthode que tu ajoutes
            } else {
                JOptionPane.showMessageDialog(this, "Paiement refusé. Veuillez vérifier vos informations.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Crée un panneau global pour le bas de la fenêtre
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(new FooterBar(), BorderLayout.SOUTH);
        southPanel.add(boutonPayer, BorderLayout.CENTER);

        // Et ajoute ce panneau au sud de la fenêtre principale
        add(southPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
