// ======================= LOGIN VIEW =======================
package login;

import client.ClientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton goToRegisterButton;
    private LoginController controller;

    public LoginView() {
        controller = new LoginController();

        setTitle("Connexion - Legendaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // ===== Bandeau haut =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(800, 50));

        JLabel logo = new JLabel(loadImage("logo.png", 40, 40));
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Retour vers la page d'accueil (à implémenter)");
            }
        });

        JLabel title = new JLabel("Legendaria", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 25));

        JButton btnCompte = new JButton(loadImage("compte.png", 40, 40));
        btnCompte.setBorderPainted(false);
        btnCompte.setContentAreaFilled(false);

        topBar.add(logo, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(btnCompte, BorderLayout.EAST);

        // ===== Formulaire de connexion =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 30));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));

        loginButton = new JButton("Se connecter");
        goToRegisterButton = new JButton("Créer un compte");

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(loginButton);
        formPanel.add(goToRegisterButton);

        // ===== Footer =====
        JLabel footer = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Actions : tester la connexion
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            ClientModel client = controller.attemptLogin(Map.of(
                    "email", email,
                    "password", password
            ));

            if (client.getClientID() == 7) {
                JOptionPane.showMessageDialog(this,
                        "Identifiants incorrects. Connexion en tant qu'invité.",
                        "Échec", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Bienvenue " + client.getName() + " (Type: " + client.getRole() + ")",
                        "Connexion réussie", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Bouton pour l'inscription (à venir)
        goToRegisterButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Redirection vers la page d'inscription (à coder)");
        });
    }

    private ImageIcon loadImage(String filename, int width, int height) {
        java.net.URL resource = getClass().getResource("/attraction/images/" + filename);
        if (resource == null) {
            System.err.println("❌ Image non trouvée : " + filename);
            return new ImageIcon();
        }
        ImageIcon icon = new ImageIcon(resource);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
