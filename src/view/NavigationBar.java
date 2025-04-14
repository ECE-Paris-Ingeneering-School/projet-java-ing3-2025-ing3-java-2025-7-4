package view;

// --- NavigationBar.java ---
import javax.swing.*;
import java.awt.*;

public class NavigationBar extends JPanel {
    public NavigationBar(boolean isLoggedIn, boolean isAdmin, String userName) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setBackground(new Color(230, 230, 230));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton accueilBtn = new JButton("Accueil");
        leftPanel.add(accueilBtn);
        if (isAdmin) {
            JButton adminBtn = new JButton("Admin");
            leftPanel.add(adminBtn);
        }

        JLabel title = new JLabel("Parc Attraction");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(title);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        if (isLoggedIn) {
            JButton compteBtn = new JButton("Mon compte (" + userName + ")");
            rightPanel.add(compteBtn);
        } else {
            rightPanel.add(new JButton("Se connecter"));
            rightPanel.add(new JButton("S'inscrire"));
        }

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
}
