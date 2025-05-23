//Packages
package view.Attraction;

//Import nécessaire

import DAO.DaoFactory;

import Controller.Attraction.AttractionController;
import Controller.Reservation.ReservationController;

import Model.Attraction.AttractionModel;
import Model.Client.ClientModel;

import view.Assets.FooterBar;
import view.Connect.LoginView;
import view.Reservation.ReservationView;

import toolbox.SessionManager;
import toolbox.NavigationBar;


import javax.swing.*;
import java.awt.*;
import java.util.List;

import static toolbox.ImageLoader.loadImage;
import static toolbox.SoundPlayer.playSound;

public class AttractionView extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final AttractionController controller;
    private AttractionModel attractionCourante;

    private JLabel detailImage, detailTitre, detailDescription, detailPrix;

    public AttractionView() {
        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        controller = new AttractionController(daoFactory);

        setTitle("Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ BARRE DE NAVIGATION
        add(new NavigationBar("Legendaria"), BorderLayout.NORTH);

        // ✅ PANEL PRINCIPAL
        add(mainPanel, BorderLayout.CENTER);
        add(new FooterBar(),BorderLayout.SOUTH);

        buildAccueilPanel();
        buildDetailPanel();

        cardLayout.show(mainPanel, "accueil");
        setVisible(true);
    }

    private void buildAccueilPanel() {
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
                    if (attraction.getName().equalsIgnoreCase("Dragon")) playSound("rugissement.wav");
                    if (attraction.getName().equalsIgnoreCase("Zeus")) playSound("eclair.wav");
                    if (attraction.getName().equalsIgnoreCase("Carroussel")) playSound("carrousel.wav");
                    if (attraction.getName().equalsIgnoreCase("Meduse Express")) playSound("meduse.wav");
                    if (attraction.getName().equalsIgnoreCase("Par Isis")) playSound("isis.wav");
                    if (attraction.getName().equalsIgnoreCase("Belenos")) playSound("belenos.wav");
                }
            });

            JLabel name = new JLabel(attraction.getName(), SwingConstants.CENTER);
            card.add(img, BorderLayout.CENTER);
            card.add(name, BorderLayout.SOUTH);
            grid.add(card);
        }

        accueilPanel.add(grid, BorderLayout.CENTER);
        mainPanel.add(accueilPanel, "accueil");
        add(new FooterBar(),BorderLayout.SOUTH);
    }

    private void buildDetailPanel() {
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
            ClientModel user = SessionManager.getCurrentUser();

            if (user == null) {
                int choix = JOptionPane.showOptionDialog(this, "Souhaitez-vous vous connecter pour retrouver votre réservation plus tard ?", "Réservation en invité", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Se connecter", "Continuer en invité"}, "Se connecter");

                if (choix == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginView(); // ou NavigationBarHelper.openLoginView(this);
                    return;
                }

                // Sinon : continuer en invité
                user = SessionManager.createGuestUser();
                SessionManager.setCurrentUser(user);
            }

            ReservationView reservationView = new ReservationView();
            reservationView.setAttraction(attractionCourante);
            reservationView.setClient(user);

            ReservationController controller = new ReservationController(reservationView);

            dispose();
        });

        content.add(detailImage);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(detailTitre);
        content.add(detailDescription);
        content.add(detailPrix);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(reserver);



        detailPanel.add(content, BorderLayout.CENTER);

        mainPanel.add(detailPanel, "detail");
        setVisible(true);
    }

    private void showDetail(AttractionModel attraction) {
        detailImage.setIcon(loadImage(attraction.getImage(), 300, 200));
        detailTitre.setText(attraction.getName());
        detailDescription.setText("<html><p style='width:600px'>" + attraction.getDescription() + "</p></html>");
        detailPrix.setText("Prix : " + attraction.getPrix() + " €");
        cardLayout.show(mainPanel, "detail");
        this.attractionCourante = attraction;
    }
}

