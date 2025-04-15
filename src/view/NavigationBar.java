package view;

import Model.ClientModel;
import toolbox.SessionManager;
import toolbox.NavigationBarHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NavigationBar extends JPanel {
    public NavigationBar(String siteTitle) {
        ClientModel user = SessionManager.getCurrentUser();
        boolean isLoggedIn = user != null;
        String userName = isLoggedIn ? user.getFullName() : "Invité";

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 50));
        setBackground(Color.BLACK);

        // ---- Logo à gauche ----
        ImageIcon icon = loadImage("logo.png", 40, 40);
        JButton logoBtn = new JButton(icon);
        logoBtn.setBorderPainted(false);
        logoBtn.setContentAreaFilled(false);
        logoBtn.setFocusPainted(false);
        logoBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoBtn.addActionListener((ActionEvent e) -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            NavigationBarHelper.openAttractionView(frame, user);
        });

        // ---- Titre au centre ----
        JLabel title = new JLabel(siteTitle, SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 20));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(title);

        // ---- Boutons à droite ----
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        JButton compteBtn = new JButton(loadImage("compte.png", 40, 40));
        compteBtn.setBorderPainted(false);
        compteBtn.setContentAreaFilled(false);
        compteBtn.setFocusPainted(false);
        compteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        compteBtn.setToolTipText(isLoggedIn ? "Mon compte" : "Se connecter");

        compteBtn.addActionListener((ActionEvent e) -> {
            if (isLoggedIn) {
                new ClientDashBoardView();
            } else {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                NavigationBarHelper.openLoginView(currentFrame);
            }
        });

        rightPanel.add(compteBtn);

        add(logoBtn, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    private ImageIcon loadImage(String filename, int width, int height) {
        try {
            java.net.URL resource = getClass().getClassLoader().getResource("images/" + filename);
            if (resource == null) {
                System.err.println(" Image non trouvée : images/" + filename);
                return new ImageIcon();
            }
            ImageIcon icon = new ImageIcon(resource);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }
}