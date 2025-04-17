//Package
package view.Client;


//Import
import DAO.DaoFactory;

import Controller.Client.ClientController;
import Controller.Reservation.PlanningController;
import Controller.Stats.StatsController;


import Model.Client.ClientModel;
import Model.Reservation.ReservationModel;

import view.Assets.FooterBar;
import view.Attraction.AttractionAdminView;
import view.Reservation.PlanningView;
import view.Reservation.ReservationListView;
import view.Connect.UserManagementView;

import toolbox.SessionManager;
import toolbox.NavigationBarHelper;
import toolbox.NavigationBar;

import javax.swing.*;
import java.awt.*;

public class ClientDashBoardView extends JFrame {

    private final ClientController controller;

    public ClientDashBoardView() {
        ClientModel user = SessionManager.getCurrentUser();
        boolean isAdmin = user != null && user.getAccountType() == 2;

        controller = new ClientController(DaoFactory.getInstance("attractions_db", "root", ""));

        setTitle("Espace personnel - Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new NavigationBar("Espace personnel"), BorderLayout.NORTH);

        if (SessionManager.isGuest()) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

            JLabel message = new JLabel("Accès interdit aux invités.");
            message.setFont(new Font("Serif", Font.BOLD, 18));
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(message);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));

            JButton logoutBtn = new JButton("Se déconnecter");
            logoutBtn.setBackground(Color.RED);          // Couleur de fond
            logoutBtn.setForeground(Color.WHITE);        // Couleur du texte
            logoutBtn.setOpaque(true);
            logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoutBtn.addActionListener(e -> {
                dispose();
                NavigationBarHelper.openLoginView(null);
            });
            panel.add(logoutBtn);

            add(panel, BorderLayout.CENTER);
            add(new FooterBar(), BorderLayout.SOUTH);
            setVisible(true);
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel welcomeLabel = new JLabel("Bienvenue, " + user.getFullName());
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btnInfos = new JButton("Mes informations");
        btnInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInfos.addActionListener(e -> new ClientInfosView(user)); // passe par constructeur
        panel.add(btnInfos);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnRDV = new JButton("Voir mes rendez-vous");
        btnRDV.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRDV.addActionListener(e -> {
            dispose();
            new ReservationListView();
        });
        panel.add(btnRDV);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        if (isAdmin) {
            JButton btnAttractions = new JButton("Gérer les attractions");
            btnAttractions.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnAttractions.addActionListener(e -> {
                dispose();
                new AttractionAdminView(user.getAccountType());
            });
            panel.add(btnAttractions);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JButton btnClients = new JButton("Gérer les clients");
            btnClients.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnClients.addActionListener(e -> {
                dispose();
                new UserManagementView();
            });
            panel.add(btnClients);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JButton btnStats = new JButton("Afficher les statistiques");
            btnStats.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnStats.addActionListener(e -> {
                dispose();
                StatsController reporting = new StatsController(
                        DaoFactory.getInstance("attractions_db", "root", "").getOrdersDAO());
                reporting.displayStats();
            });
            panel.add(btnStats);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            JButton btnPlanning = new JButton("Gérer le planning");
            btnPlanning.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnPlanning.addActionListener(e -> {
                dispose();
                PlanningView planningView = new PlanningView(1);
                new PlanningController(planningView, new ReservationModel(0, 0, 0, 0, 0, 0, null));
                planningView.setVisible(true);
            });
            panel.add(btnPlanning);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton btnLogout = new JButton("Se déconnecter");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setForeground(Color.RED);
        btnLogout.addActionListener(e -> {
            dispose();
            NavigationBarHelper.openLoginView(null);
        });

        panel.add(btnLogout);
        add(panel);
        add(new FooterBar(),BorderLayout.SOUTH);
        setVisible(true);
    }
}
