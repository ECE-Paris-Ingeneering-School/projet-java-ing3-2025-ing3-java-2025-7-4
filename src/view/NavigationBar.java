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
        boolean isAdmin = isLoggedIn && user.getAccountType() == 2;
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
            NavigationBarHelper.openAttractionView((JFrame) SwingUtilities.getWindowAncestor(this), user);
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
            if (!isLoggedIn) {
                NavigationBarHelper.openLoginView((JFrame) SwingUtilities.getWindowAncestor(this));
                return;
            }

            JPopupMenu menu = new JPopupMenu();

            JMenuItem infos = new JMenuItem("Mes informations");
            infos.addActionListener(ev -> new ClientInfosView());
            menu.add(infos);

            JMenuItem rdv = new JMenuItem("Mes rendez-vous");
            rdv.addActionListener(ev -> JOptionPane.showMessageDialog(this, "Affichage des rendez-vous (non implémenté)."));
            menu.add(rdv);

            if (isAdmin) {
                JMenuItem manageAttr = new JMenuItem("Gérer les attractions");
                manageAttr.addActionListener(ev -> JOptionPane.showMessageDialog(this, "Gestion des attractions (non implémentée)."));
                menu.add(manageAttr);

                JMenuItem manageUsers = new JMenuItem("Gérer les clients");
                manageUsers.addActionListener(ev -> JOptionPane.showMessageDialog(this, "Gestion des clients (non implémentée)."));
                menu.add(manageUsers);

                JMenuItem stats = new JMenuItem("Voir les statistiques");
                stats.addActionListener(ev -> JOptionPane.showMessageDialog(this, "Affichage des statistiques (non implémenté)."));
                menu.add(stats);
            }

            JMenuItem logout = new JMenuItem("Se déconnecter");
            logout.addActionListener(ev -> {
                JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
                SessionManager.logout();
                current.dispose();
                NavigationBarHelper.openAttractionView(null,null);
            });
            menu.add(logout);

            menu.show(compteBtn, 0, compteBtn.getHeight());
        });

        rightPanel.add(compteBtn);

        add(logoBtn, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    private ImageIcon loadImage(String filename, int width, int height) {
        try {
            // ✅ On enlève le '/' du début : chemin RELATIF depuis le classpath
            java.net.URL resource = getClass().getClassLoader().getResource("images/" + filename);

            if (resource == null) {
                System.err.println(" Image non trouvée : images/" + filename);
                return new ImageIcon(); // retourne une image vide
            }

            ImageIcon icon = new ImageIcon(resource);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);

        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon(); // fallback image vide
        }
    }

}
