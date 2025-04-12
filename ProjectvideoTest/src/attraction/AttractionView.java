package attraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AttractionView extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel accueilPanel;
    private JPanel detailPanel;
    private JLabel imageDetail;
    private JLabel descriptionDetail;
    private JLabel prixDetail;
    private JLabel titreDetail;

    private AttractionController controller;
    private List<AttractionModel> attractions;

    public AttractionView() {
        controller = new AttractionController();
        attractions = controller.getAllAttractions();

        setTitle("Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        buildAccueilPanel();
        buildDetailPanel();

        add(mainPanel);
        setVisible(true);
    }

    private void buildAccueilPanel() {
        accueilPanel = new JPanel(new BorderLayout());

        // Bandeau haut
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(800, 50));

        JLabel logo = new JLabel(loadImage("logo.png", 40, 40));
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "accueil");
            }
        });

        JLabel title = new JLabel("Legendaria", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 20));

        JButton btnCompte = new JButton(loadImage("compte.png", 40, 40));
        btnCompte.setBorderPainted(false);
        btnCompte.setContentAreaFilled(false);

        topBar.add(logo, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(btnCompte, BorderLayout.EAST);

        // Grille d’attractions
        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (AttractionModel attraction : attractions) {
            JPanel card = new JPanel(new BorderLayout());

            JLabel img = new JLabel(loadImage(attraction.getImage(), 150, 150));
            img.setHorizontalAlignment(SwingConstants.CENTER);
            img.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            img.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showDetail(attraction);
                }
            });

            JLabel name = new JLabel(attraction.getNom(), SwingConstants.CENTER);
            card.add(img, BorderLayout.CENTER);
            card.add(name, BorderLayout.SOUTH);
            grid.add(card);
        }

        // Footer
        JLabel credits = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        credits.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        accueilPanel.add(topBar, BorderLayout.NORTH);
        accueilPanel.add(grid, BorderLayout.CENTER);
        accueilPanel.add(credits, BorderLayout.SOUTH);

        mainPanel.add(accueilPanel, "accueil");
    }

    private void buildDetailPanel() {
        detailPanel = new JPanel(new BorderLayout());

        // Bandeau haut
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(800, 50));

        JLabel logo = new JLabel(loadImage("logo.png", 40, 40));
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "accueil");
            }
        });

        titreDetail = new JLabel("Attraction", SwingConstants.CENTER);
        titreDetail.setForeground(Color.WHITE);
        titreDetail.setFont(new Font("Serif", Font.BOLD, 20));

        JButton btnCompte = new JButton(loadImage("compte.png", 40, 40));
        btnCompte.setBorderPainted(false);
        btnCompte.setContentAreaFilled(false);

        topBar.add(logo, BorderLayout.WEST);
        topBar.add(titreDetail, BorderLayout.CENTER);
        topBar.add(btnCompte, BorderLayout.EAST);

        // Corps de l’attraction
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        imageDetail = new JLabel();
        imageDetail.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionDetail = new JLabel();
        descriptionDetail.setAlignmentX(Component.CENTER_ALIGNMENT);

        prixDetail = new JLabel();
        prixDetail.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton reserverBtn = new JButton("Réserver");
        reserverBtn.setBackground(Color.GREEN);
        reserverBtn.setForeground(Color.WHITE);
        reserverBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        reserverBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(imageDetail);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(descriptionDetail);
        content.add(prixDetail);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(reserverBtn);

        // Footer
        JLabel credits = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        credits.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        detailPanel.add(topBar, BorderLayout.NORTH);
        detailPanel.add(content, BorderLayout.CENTER);
        detailPanel.add(credits, BorderLayout.SOUTH);

        mainPanel.add(detailPanel, "detail");
    }

    private void showDetail(AttractionModel attraction) {
        imageDetail.setIcon(loadImage(attraction.getImage(), 300, 200));
        titreDetail.setText(attraction.getNom());
        descriptionDetail.setText("<html><p style='width:600px'>" + attraction.getDescription() + "</p></html>");
        prixDetail.setText("Prix : " + attraction.getPrix() + " €");

        cardLayout.show(mainPanel, "detail");
    }

    private ImageIcon loadImage(String filename, int width, int height) {
        java.net.URL resource = getClass().getResource("/attraction/images/" + filename);
        if (resource == null) {
            System.err.println("❌ Image non trouvée : " + "/attraction/images/" + filename);
            return new ImageIcon(); // image vide pour éviter le crash
        }
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttractionView::new);
    }
}
