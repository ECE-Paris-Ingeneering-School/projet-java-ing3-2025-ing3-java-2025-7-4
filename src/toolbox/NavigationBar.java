package toolbox;

import Model.Client.ClientModel;
import view.Client.ClientDashBoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static toolbox.ImageLoader.loadImage;

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
            Window window = SwingUtilities.getWindowAncestor(this);
            NavigationBarHelper.openAttractionView(window, user);

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

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame frame) {
                frame.dispose();
            }

            if (isLoggedIn) {
                new ClientDashBoardView();
            }
            else {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                NavigationBarHelper.openLoginView(currentFrame);
            }
        });

        rightPanel.add(compteBtn);

        add(logoBtn, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

}