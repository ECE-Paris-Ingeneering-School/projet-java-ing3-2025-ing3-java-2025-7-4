package view;

import Model.ReservationModel;

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
    private ReservationModel reservationModel;

    public PlanningView(int page) {
        setTitle("Planning Mensuel - MVC");
        int height = 600;
        int width = 800;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        NavigationBar topBar = new NavigationBar("Planning");
        topBar.setBounds(0, 0, width, 50);
        add(topBar);

        // Panneau d'en-tête avec les boutons et le label du mois
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        monthLabel = new JLabel("");

        prevButton.setBounds(10, 60, 80, 30);
        nextButton.setBounds(width - 105, 60, 80, 30);
        monthLabel.setBounds(width / 2 - 50, 60, 600, 30);

        add(prevButton);
        add(nextButton);
        add(monthLabel);

        planningPanel = new JPanel();
        planningPanel.setLayout(new GridLayout(0, 7));
        planningPanel.setBounds(10, 100, width - 35, 370);
        planningPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(planningPanel);

        if (page == 0) {
            JPanel square1 = new JPanel();
            JLabel desc1 = new JLabel("Jour Spécial 40€");
            square1.setBackground(new Color(72, 255, 255));
            square1.setBounds(10, 480, 15, 15);
            desc1.setBounds(25, 480, 100, 15);
            add(square1);
            add(desc1);

            JPanel square2 = new JPanel();
            JLabel desc2 = new JLabel("Haute Saison 25€");
            square2.setBackground(new Color(218, 45, 4, 137));
            square2.setBounds(165, 480, 15, 15);
            desc2.setBounds(180, 480, 100, 15);
            add(square2);
            add(desc2);

            JPanel square3 = new JPanel();
            JLabel desc3 = new JLabel("Basse Saison 20€");
            square3.setBackground(new Color(234, 197, 4, 137));
            square3.setBounds(320, 480, 15, 15);
            desc3.setBounds(335, 480, 150, 15);
            add(square3);
            add(desc3);

            JLabel desc4 = new JLabel("-30% pour les enfant et gratuit pour les moins de 4 ans");
            desc4.setBounds(465, 480, 350, 15);
            add(desc4);

            validateButton1 = new JButton("Valider");
            validateButton1.setOpaque(true);
            validateButton1.setBackground(Color.GREEN);
            validateButton1.setBounds((width / 2) - 100, 500, 200, 40);
            add(validateButton1);
        }

        if (page == 1) {
            option1 = new JRadioButton("Jour Spécial 40€");
            option2 = new JRadioButton("Haute Saison 25€");
            option3 = new JRadioButton("Basse Saison 20€");

            ButtonGroup group = new ButtonGroup();
            group.add(option1);
            group.add(option2);
            group.add(option3);

            JPanel square1 = new JPanel();
            square1.setBackground(new Color(72, 255, 255));
            square1.setBounds(10, 480, 15, 15);
            option1.setBounds(25, 480, 150, 15);
            add(square1);

            JPanel square2 = new JPanel();
            square2.setBackground(new Color(218, 45, 4, 137));
            square2.setBounds(165, 480, 15, 15);
            option2.setBounds(180, 480, 150, 15);
            add(square2);

            JPanel square3 = new JPanel();
            square3.setBackground(new Color(234, 197, 4, 137));
            square3.setBounds(320, 480, 15, 15);
            option3.setBounds(335, 480, 150, 15);
            add(square3);

            add(option1);
            add(option2);
            add(option3);

            validateButton2 = new JButton("Valider");
            validateButton2.setOpaque(true);
            validateButton2.setBackground(Color.GREEN);
            validateButton2.setBounds((width / 2) - 100, 500, 200, 40);
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

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public void setMonthLabel(String text) {
        monthLabel.setText(text);
    }

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
