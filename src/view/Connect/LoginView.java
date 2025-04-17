// ======================= LOGIN VIEW =======================
package view.Connect;

import Controller.Connect.LoginController;
import Controller.Connect.RegistrationController;
import Model.Client.ClientModel;
import toolbox.SessionManager;
import toolbox.NavigationBarHelper;
import toolbox.NavigationBar;
import view.Assets.FooterBar;

import javax.swing.*;
import java.awt.*;
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


        // ===== NavigationBar (avec logo et bouton compte) =====
        panel.add(new NavigationBar("Legendaria"), BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(new FooterBar(), BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        // Actions : tester la connexion
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            boolean isAuthenticated = controller.handleLogin(Map.of(
                    "email", email,
                    "password", password
            ));
            System.out.println("isAuthenticated = " + isAuthenticated);
            //System.out.println("Session user : " + SessionManager.getCurrentUser().getFullName());



            if (!isAuthenticated) {
                JOptionPane.showMessageDialog(this,
                        "Identifiants incorrects, connexion échouée.",
                        "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                NavigationBarHelper.openAttractionView(this, controller.getCurrentUser());
            }
        });
        goToRegisterButton.addActionListener(e -> {
            dispose();
            new RegistrationView(new RegistrationController());
        });


    }
}
