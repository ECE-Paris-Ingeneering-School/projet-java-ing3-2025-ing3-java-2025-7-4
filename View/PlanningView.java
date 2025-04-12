package View;

import javax.swing.*;
import java.awt.*;

public class PlanningView extends JFrame {
    private JButton prevButton;
    private JButton nextButton;
    private JLabel monthLabel;
    private JPanel planningPanel;

    public PlanningView() {
        setTitle("Planning Mensuel - MVC");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panneau d'en-tête avec les boutons et le label du mois
        JPanel headerPanel = new JPanel(new BorderLayout());
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Panneau pour afficher la grille du planning
        planningPanel = new JPanel();
        planningPanel.setLayout(new GridLayout(0, 7)); // 7 colonnes pour chaque jour de la semaine
        add(planningPanel, BorderLayout.CENTER);

        // Centre la fenêtre sur l'écran
        setLocationRelativeTo(null);
    }

    // Accesseurs pour les boutons (utilisés par le contrôleur)
    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    // Met à jour le label affichant le mois et l'année
    public void setMonthLabel(String text) {
        monthLabel.setText(text);
    }

    // Méthodes pour manipuler le contenu du panneau planning
    public void clearPlanningPanel() {
        planningPanel.removeAll();
    }

    public void addToPlanningPanel(Component comp) {
        planningPanel.add(comp);
    }

    public void refreshPlanningPanel() {
        planningPanel.revalidate();
        planningPanel.repaint();
    }
}
