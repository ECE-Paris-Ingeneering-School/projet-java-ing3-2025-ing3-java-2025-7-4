package view;

import javax.swing.*;
import java.awt.*;

public class PlanningView extends JFrame {
    private JButton prevButton;
    private JButton nextButton;
    private JLabel monthLabel;
    private JPanel planningPanel;
    private JButton validateButton1;
    private JButton validateButton2;
    private JRadioButton option1;
    private JRadioButton option2;
    private JRadioButton option3;

    public PlanningView(int page) {
        setTitle("Planning Mensuel - MVC");
        int height = 600;
        int width = 800;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Panneau d'en-tête avec les boutons et le label du mois
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        monthLabel = new JLabel("");

        // Positionnement en pixels dans le header
        prevButton.setBounds(10, 50, 80, 30);   // x, y, largeur, hauteur
        nextButton.setBounds(width - 105, 50, 80, 30);
        monthLabel.setBounds(width/2 - 50, 50, 600, 30);

        add(prevButton);
        add(nextButton);
        add(monthLabel);

        // Création du panneau planning (la grille) avec un GridLayout
        planningPanel = new JPanel();
        planningPanel.setLayout(new GridLayout(0, 7)); // Grille avec 7 colonnes (jours de la semaine)
        // Positionné de manière absolue pour commencer à x=10, y=50 et se terminer à x=590, y=600
        planningPanel.setBounds(10, 90, width-35, 400);
        // Optionnel : Ajout d'une bordure pour visualiser la zone
        planningPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(planningPanel);


        if (page == 0) {
            JPanel square1 = new JPanel();
            JLabel desc1 = new JLabel("Jour Spécial 40€");
            square1.setBackground(new Color(72, 255, 255));
            square1.setBounds(10, 500, 15, 15);
            desc1.setBounds(25, 500, 100, 15);
            add(square1);
            add(desc1);

            JPanel square2 = new JPanel();
            JLabel desc2 = new JLabel("Haute Saison 25€");
            square2.setBackground(new Color(218, 45, 4, 137));
            square2.setBounds(165, 500, 15, 15);
            desc2.setBounds(180, 500, 100, 15);
            add(square2);
            add(desc2);

            JPanel square3 = new JPanel();
            JLabel desc3 = new JLabel("Basse Saison 20€");
            square3.setBackground(new Color(234, 197, 4, 137));
            square3.setBounds(320, 500, 15, 15);
            desc3.setBounds(335, 500, 150, 15);
            add(square3);
            add(desc3);

            JLabel desc4 = new JLabel("-30% pour les enfant et gratuit pour les moins de 4 ans");
            desc4.setBounds(465, 500, 350, 15);
            add(desc4);



            // Panneau pour le bouton "Valider"
            validateButton1 = new JButton("Valider");
            validateButton1.setOpaque(true);
            validateButton1.setBackground(Color.GREEN);
            validateButton1.setBounds((width/2) - 100, 520, 200, 40);
            add(validateButton1);
        }

        if (page == 1) {

            // Création des boutons radio
            option1 = new JRadioButton("Jour Spécial 40€");
            option2 = new JRadioButton("Haute Saison 25€");
            option3 = new JRadioButton("Basse Saison 20€");

            // Regroupement dans un ButtonGroup pour assurer l'exclusivité
            ButtonGroup group = new ButtonGroup();
            group.add(option1);
            group.add(option2);
            group.add(option3);

            JPanel square1 = new JPanel();
            square1.setBackground(new Color(72, 255, 255));
            square1.setBounds(10, 500, 15, 15);
            option1.setBounds(25, 500, 150, 15);
            add(square1);

            JPanel square2 = new JPanel();
            square2.setBackground(new Color(218, 45, 4, 137));
            square2.setBounds(165, 500, 15, 15);
            option2.setBounds(180, 500, 150, 15);
            add(square2);

            JPanel square3 = new JPanel();
            square3.setBackground(new Color(234, 197, 4, 137));
            square3.setBounds(320, 500, 15, 15);
            option3.setBounds(335, 500, 150, 15);
            add(square3);

            // Ajout des boutons au conteneur
            add(option1);
            add(option2);
            add(option3);

            // Panneau pour le bouton "Valider"
            validateButton2 = new JButton("Valider");
            validateButton2.setOpaque(true);
            validateButton2.setBackground(Color.GREEN);
            validateButton2.setBounds((width/2) - 100, 520, 200, 40);
            add(validateButton2);
        }

        setLocationRelativeTo(null);
    }

    public JButton getValidateButton1() {
        return validateButton1;
    }

    public JButton getValidateButton2() {
        return validateButton2;
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

    public JRadioButton getOption1() {
        return option1;
    }

    public JRadioButton getOption2() {
        return option2;
    }

    public JRadioButton getOption3() {
        return option3;
    }

}