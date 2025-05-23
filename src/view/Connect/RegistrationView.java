package view.Connect;

import Controller.Connect.RegistrationController;
import toolbox.NavigationBar;
import view.Assets.FooterBar;

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

    public RegistrationView(RegistrationController controller) {
        this.controller = controller;

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



        panel.add(new NavigationBar("Legendaria"), BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(new FooterBar(), BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        registerButton.addActionListener(e -> {
            String firstname = firstnameField.getText().trim();
            String surname = surnameField.getText().trim();
            String birthdate = birthdateField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String ageText = ageField.getText().trim();

            if (firstname.isEmpty() || surname.isEmpty() || birthdate.isEmpty()
                    || email.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);

                RegistrationController.RegistrationResult result = controller.handleRegistration(Map.of(
                        "firstname", firstname,
                        "surname", surname,
                        "birthdate", birthdate,
                        "email", email,
                        "password", password,
                        "age", String.valueOf(age)
                ));

                switch (result) {
                    case SUCCESS -> {
                        JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                        dispose();
                        new LoginView();
                    }
                    case EMAIL_EXISTS -> JOptionPane.showMessageDialog(this,
                            "Email déjà utilisé. Choisissez un autre.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    case ERROR -> JOptionPane.showMessageDialog(this,
                            "Une erreur est survenue. Veuillez réessayer plus tard.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'âge doit être un nombre entier.");
            }
        });

    }
}
