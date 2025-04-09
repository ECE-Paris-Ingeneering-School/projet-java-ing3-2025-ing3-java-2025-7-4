package View;

import javax.swing.*;
import java.awt.*;


/**
 * @author erelr
 *
 * Il s'agit du View de planning ça sert à afficher le planning
 *
 */
public class PlanningView extends JFrame {
    private JButton prevButton;
    private JButton nextButton;
    private JLabel monthLabel;
    private JPanel calendarPanel;

    public PlanningView() {
        setTitle("Calendrier Mensuel - MVC");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Création du panneau d'en-tête avec les boutons et le label du mois
        JPanel headerPanel = new JPanel(new BorderLayout());
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        monthLabel = new JLabel("", SwingConstants.CENTER);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Création du panneau qui affichera la grille du calendrier
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7)); // Grille dynamique avec 7 colonnes (un pour chaque jour)
        add(calendarPanel, BorderLayout.CENTER);

        // Centre la fenêtre à l'écran
        setLocationRelativeTo(null);
    }

    // Accesseurs pour les boutons, utiles au contrôleur
    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    // Permet de mettre à jour le label affichant le mois et l'année
    public void setMonthLabel(String text) {
        monthLabel.setText(text);
    }

    // Méthodes pour gérer le contenu du panneau calendrier
    public void clearCalendarPanel() {
        calendarPanel.removeAll();
    }

    public void addToCalendarPanel(Component comp) {
        calendarPanel.add(comp);
    }

    public void refreshCalendarPanel() {
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
}

