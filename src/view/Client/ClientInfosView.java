package view.Client;

import Model.Client.ClientModel;

import javax.swing.*;
import java.awt.*;

public class ClientInfosView extends JFrame {

    public ClientInfosView(ClientModel user) {
        setTitle("Mes informations - Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titre = new JLabel("Mes informations personnelles", SwingConstants.CENTER);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("Serif", Font.BOLD, 18));

        panel.add(titre);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(new JLabel("Nom complet : " + user.getFullName()));
        panel.add(new JLabel("Email : " + user.getEmail()));
        panel.add(new JLabel("Rôle : " + user.getAccountType()));
        panel.add(new JLabel("Age : " + user.getAge()));
        panel.add(new JLabel("Date de naissance : " + user.getBirthdate()));

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton retourBtn = new JButton("Retour");
        retourBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        retourBtn.addActionListener(e -> dispose());

        panel.add(retourBtn);

        add(panel);
        setVisible(true);
    }
}
