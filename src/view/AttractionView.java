package view;

// --- AttractionView.java (version unifiée avec accueil et détail) ---

import Controller.AttractionController;
import Controller.PaymentController;
import DAO.DaoFactory;
import Model.AttractionModel;
import Model.OrdersModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static Model.SoundPlayer.playSound;

public class AttractionView extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final AttractionController controller;
    private AttractionModel attractionCourante;


    private JLabel detailImage, detailTitre, detailDescription, detailPrix;

    public AttractionView(boolean isLoggedIn, boolean isAdmin, String userName) {
        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        controller = new AttractionController(daoFactory);

        setTitle("Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new NavigationBar("Legendaria", isLoggedIn, isAdmin, userName), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        buildAccueilPanel(isLoggedIn, isAdmin, userName);
        buildDetailPanel(isLoggedIn, isAdmin, userName);

        cardLayout.show(mainPanel, "accueil");
        setVisible(true);
    }

    private void buildAccueilPanel(boolean isLoggedIn, boolean isAdmin, String userName) {
        JPanel accueilPanel = new JPanel(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<AttractionModel> attractions = controller.getAllAttractions();
        for (AttractionModel attraction : attractions) {
            JPanel card = new JPanel(new BorderLayout());
            JLabel img = new JLabel(loadImage(attraction.getImage(), 150, 150));
            img.setHorizontalAlignment(SwingConstants.CENTER);
            img.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            img.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    showDetail(attraction);
                    // 🔊 Lecture du son en fonction de l'attraction
                    if (attraction.getName().equalsIgnoreCase("Dragon")) {
                        playSound("rugissement.wav");
                    } else if (attraction.getName().equalsIgnoreCase("Zeus")) {
                        playSound("eclair.wav");
                    }
                    // Ajoute autant de cas que tu veux !
                }
            });

            JLabel name = new JLabel(attraction.getName(), SwingConstants.CENTER);
            card.add(img, BorderLayout.CENTER);
            card.add(name, BorderLayout.SOUTH);
            grid.add(card);
        }

        JLabel credits = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        credits.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        accueilPanel.add(grid, BorderLayout.CENTER);
        accueilPanel.add(credits, BorderLayout.SOUTH);
        mainPanel.add(accueilPanel, "accueil");
    }

    private void buildDetailPanel(boolean isLoggedIn, boolean isAdmin, String userName) {
        JPanel detailPanel = new JPanel(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        detailImage = new JLabel();
        detailImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailTitre = new JLabel("Titre", SwingConstants.CENTER);
        detailTitre.setFont(new Font("Serif", Font.BOLD, 22));
        detailTitre.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailDescription = new JLabel();
        detailDescription.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailPrix = new JLabel();
        detailPrix.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton reserver = new JButton("Réserver");
        reserver.setAlignmentX(Component.CENTER_ALIGNMENT);
        reserver.setBackground(Color.GREEN);
        reserver.setForeground(Color.WHITE);
        reserver.addActionListener(e -> {
            // 👇 Création d'une fausse commande simulée
            OrdersModel fakeOrder = new OrdersModel(
                    0,                           // Pas encore inséré dans la BDD
                    java.time.LocalDateTime.now(),
                    1,                           // 1 personne par défaut
                    (float) attractionCourante.getPrix(), // prix de l'attraction
                    "Pending",                  // statut initial
                    attractionCourante.getAttractionID(),
                    0                           // pas de réservation réelle pour l'instant
            );

            DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
            PaymentController paiementCtrl = new PaymentController(daoFactory.getOrdersDAO());

            // Ouverture directe de la vue de paiement
            new PaymentView(fakeOrder, paiementCtrl,this);
        });


        content.add(detailImage);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(detailTitre);
        content.add(detailDescription);
        content.add(detailPrix);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(reserver);

        JLabel credits = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        credits.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        detailPanel.add(content, BorderLayout.CENTER);
        detailPanel.add(credits, BorderLayout.SOUTH);
        mainPanel.add(detailPanel, "detail");
    }

    private void showDetail(AttractionModel attraction) {
        detailImage.setIcon(loadImage(attraction.getImage(), 300, 200));
        detailTitre.setText(attraction.getName());
        detailDescription.setText("<html><p style='width:600px'>" + attraction.getDescription() + "</p></html>");
        detailPrix.setText("Prix : " + attraction.getPrix() + " €");
        cardLayout.show(mainPanel, "detail");
        this.attractionCourante = attraction;
    }

    private ImageIcon loadImage(String filename, int width, int height) {
        java.net.URL resource = getClass().getResource("/images/" + filename);
        if (resource == null) System.err.println("❌ Image non trouvée : /images/" + filename);
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}