package View;

import javax.swing.*;
import java.awt.*;

public class ReservationView extends JFrame {

    private JComboBox<Integer> adultsBox;
    private JComboBox<Integer> childrenBox;
    private JComboBox<Integer> babiesBox;
    private JButton validateButton;

    public ReservationView() {
        super("Formulaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panneau principal avec GridBagLayout pour gérer la disposition
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges autour de chaque composant
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== Ligne 1 : Nombre d'adultes via JComboBox =====
        gbc.gridx = 0;    // colonne 0
        gbc.gridy = 0;    // ligne 0
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("Nombre d'adultes (+14ans)"), gbc);

        adultsBox = new JComboBox<>();
        for (int i = 0; i <= 10; i++) {
            adultsBox.addItem(i);
        }
        gbc.gridx = 1;  // colonne 1
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(adultsBox, gbc);

        // ===== Ligne 2 : Nombre d'enfants via JComboBox =====
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("Nombre d'enfants (+4ans)"), gbc);

        childrenBox = new JComboBox<>();
        for (int i = 0; i <= 10; i++) {
            childrenBox.addItem(i);
        }
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(childrenBox, gbc);

        // ===== Ligne 3 : Nombre de bébés via JComboBox =====
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("Nombre de bébés"), gbc);

        babiesBox = new JComboBox<>();
        for (int i = 0; i <= 10; i++) {
            babiesBox.addItem(i);
        }
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(babiesBox, gbc);

        // ===== Ligne 4 : Bouton Valider =====
        validateButton = new JButton("Valider");
        validateButton.setOpaque(true);
        validateButton.setBackground(Color.GREEN);
        validateButton.setBounds((800/2) - 100, 520, 200, 40);
        add(validateButton);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel);
    }

    // Getters pour accéder aux composants depuis le contrôleur
    public JComboBox<Integer> getAdultsBox() {
        return adultsBox;
    }

    public JComboBox<Integer> getChildrenBox() {
        return childrenBox;
    }

    public JComboBox<Integer> getBabiesBox() {
        return babiesBox;
    }

    public JButton getValidateButton() {
        return validateButton;
    }

}
