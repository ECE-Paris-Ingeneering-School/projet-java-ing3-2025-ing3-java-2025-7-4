// ======================= REGISTRATION VIEW =======================
package registration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Vue Swing permettant à un nouvel utilisateur de créer un compte client.
 * Elle vérifie si l'email est déjà utilisé avant d'enregistrer dans la base.
 */
public class RegistrationView extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField ageField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private RegistrationController controller;

    public RegistrationView() {
        controller = new RegistrationController();

        setTitle("Création de compte - Legendaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // ===== Bandeau haut =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(800, 50));

        JLabel title = new JLabel("Legendaria", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 25));
        topBar.add(title, BorderLayout.CENTER);

        // ===== Formulaire =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(300, 30));
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 30));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        ageField = new JTextField();
        ageField.setMaximumSize(new Dimension(300, 30));

        registerButton = new JButton("Créer le compte");
        backToLoginButton = new JButton("Retour à la connexion");

        formPanel.add(new JLabel("Nom:"));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Mot de passe:"));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Âge:"));
        formPanel.add(ageField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(registerButton);
        formPanel.add(backToLoginButton);

        // ===== Footer =====
        JLabel footer = new JLabel("© Legendaria - Projet Java 2025", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Bouton pour créer un compte
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String ageText = ageField.getText();

            if (name.isBlank() || email.isBlank() || password.isBlank() || ageText.isBlank()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                boolean created = controller.registerNewClient(Map.of(
                        "name", name,
                        "email", email,
                        "password", password,
                        "age", String.valueOf(age)
                ));

                if (created) {
                    JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                    dispose();
                    new login.LoginView();
                } else {
                    JOptionPane.showMessageDialog(this, "Email déjà utilisé. Choisissez un autre email.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'âge doit être un nombre valide.");
            }
        });

        // Bouton retour
        backToLoginButton.addActionListener(e -> {
            dispose();
            new login.LoginView();
        });
    }
}
