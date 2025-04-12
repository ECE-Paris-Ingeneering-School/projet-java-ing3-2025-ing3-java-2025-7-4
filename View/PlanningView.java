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
        setTitle("Planning Mensuel - MVC");
        int height = 700;
        int width = 600;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Panneau d'en-tête avec les boutons et le label du mois
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        // Positionnement en pixels dans le header
        prevButton.setBounds(10, 50, 80, 30);   // x, y, largeur, hauteur
        nextButton.setBounds(width - 105, 50, 80, 30);
        monthLabel.setBounds(0, 50, 600, 30);

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


        if (page == 0) {
            JPanel square1 = new JPanel();
            JLabel desc1 = new JLabel("Jour Spécial 40€");
            square1.setBackground(new Color(238, 130, 238));
            square1.setBounds(10, 560, 10, 10);
            desc1.setBounds(25, 560, 100, 10);
            add(square1);
            add(desc1);

            JPanel square2 = new JPanel();
            JLabel desc2 = new JLabel("Haute Saison 25€");
            square2.setBackground(new Color(47, 78, 193, 137));
            square2.setBounds(165, 560, 10, 10);
            desc2.setBounds(180, 560, 100, 10);
            add(square2);
            add(desc2);

            JPanel square3 = new JPanel();
            JLabel desc3 = new JLabel("Basse Saison 20€");
            square3.setBackground(Color.GREEN);
            square3.setBounds(320, 560, 10, 10);
            desc3.setBounds(335, 560, 150, 10);
            add(square3);
            add(desc3);




            // Panneau pour le bouton "Valider"
            JButton validateButton = new JButton("Valider");
            validateButton.setBounds((width/2) - 100, 600, 200, 40);
            add(validateButton);
        }

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
