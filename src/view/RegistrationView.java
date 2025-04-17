package view;

import Controller.RegistrationController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Vue Swing permettant à un nouvel utilisateur de créer un compte client.
 * Alignée avec la table `account` : prénom, nom, date de naissance, etc.
 */
public class RegistrationView extends JFrame {
    private JTextField firstnameField;
    private JTextField surnameField;
    private JTextField birthdateField;
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



        // ===== Formulaire =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        firstnameField = new JTextField();
        firstnameField.setMaximumSize(new Dimension(300, 30));
        surnameField = new JTextField();
        surnameField.setMaximumSize(new Dimension(300, 30));
        birthdateField = new JTextField(); // Format attendu : yyyy-MM-dd
        birthdateField.setMaximumSize(new Dimension(300, 30));
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 30));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 30));
        ageField = new JTextField();
        ageField.setMaximumSize(new Dimension(300, 30));

        registerButton = new JButton("Créer le compte");
        backToLoginButton = new JButton("Retour à la connexion");

        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(firstnameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Nom de famille:"));
        formPanel.add(surnameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(new JLabel("Date de naissance (yyyy-MM-dd):"));
        formPanel.add(birthdateField);
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

        panel.add(new NavigationBar("Legendaria"), BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Action: créer un compte
        registerButton.addActionListener(e -> {
            String firstname = firstnameField.getText();
            String surname = surnameField.getText();
            String birthdate = birthdateField.getText(); // ex: 2003-05-12
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String ageText = ageField.getText();

            if (firstname.isBlank() || surname.isBlank() || birthdate.isBlank()
                    || email.isBlank() || password.isBlank() || ageText.isBlank()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                boolean created = controller.registerNewClient(Map.of(
                        "firstname", firstname,
                        "surname", surname,
                        "birthdate", birthdate,
                        "email", email,
                        "password", password,
                        "age", String.valueOf(age)
                ));

                if (created) {
                    JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                    dispose();
                    new LoginView();
                } else {
                    JOptionPane.showMessageDialog(this, "Email déjà utilisé. Choisissez un autre.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'âge doit être un nombre entier.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton retour
        backToLoginButton.addActionListener(e -> {
            dispose();
            new LoginView();
        });
    }
}
