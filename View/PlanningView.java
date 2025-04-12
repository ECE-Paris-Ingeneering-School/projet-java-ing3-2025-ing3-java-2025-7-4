package View;

import javax.swing.*;
import java.awt.*;

public class PlanningView extends JFrame {
    private JButton prevButton;
    private JButton nextButton;
    private JLabel monthLabel;
    private JPanel planningPanel;
    private int page;

    public PlanningView(int page) {
        this.page = page;
        int day;
        setTitle("Planning Mensuel - MVC");
        int height = 700;
        int width = 600;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Panneau d'en-tête avec les boutons et le label du mois
        JPanel headerPanel = new JPanel(new BorderLayout());
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        // Positionnement en pixels dans le header
        prevButton.setBounds(10, 50, 80, 30);   // x, y, largeur, hauteur
        nextButton.setBounds(width - 105, 50, 80, 30);
        monthLabel.setBounds((width/2) - 300, 50, 600, 30);

        add(prevButton);
        add(nextButton);
        add(monthLabel);

        // Création du panneau planning (la grille) avec un GridLayout
        planningPanel = new JPanel();
        planningPanel.setLayout(new GridLayout(0, 7)); // Grille avec 7 colonnes (jours de la semaine)
        // Positionné de manière absolue pour commencer à x=10, y=50 et se terminer à x=590, y=600
        planningPanel.setBounds(10, 90, 575, 450);
        // Optionnel : Ajout d'une bordure pour visualiser la zone
        planningPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(planningPanel);

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
