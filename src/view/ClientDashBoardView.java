package view;

import Controller.PlanningController;
import Controller.ReportingController;
import DAO.DaoFactory;
import Model.ClientModel;
import Model.ReservationModel;
import toolbox.SessionManager;
import toolbox.NavigationBarHelper;

import javax.swing.*;
import java.awt.*;

public class ClientDashBoardView extends JFrame {

    public ClientDashBoardView() {
        ClientModel user = SessionManager.getCurrentUser();
        boolean isAdmin = user != null && user.getAccountType() == 2;

        setTitle("Espace personnel - Legendaria");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Ajout de la barre de navigation
        add(new NavigationBar("Espace personnel"), BorderLayout.NORTH);


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
        btnInfos.addActionListener(e -> new ClientInfosView());
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
                DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
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
                DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
                ReportingController controller = new ReportingController(daoFactory.getOrdersDAO());
                controller.afficherReporting();
            });
            panel.add(btnStats);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            JButton btnPlanning = new JButton("Gérer le planning");
            btnPlanning.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnPlanning.addActionListener(e -> {
                PlanningView planningView = new PlanningView(1); // Vue en mode admin
                new PlanningController(planningView,new ReservationModel(0,0,0,0,0,0,null));
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
        setVisible(true);
    }
}
