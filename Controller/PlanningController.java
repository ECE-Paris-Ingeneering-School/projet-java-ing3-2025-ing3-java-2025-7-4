package Controller;

import View.PlanningView;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
/**
 * @author erelr
 *
 * Il s'agit du controller de planning ça servira à appeler le model et la view
 * et à gérer les interactions avec la page ainsi que les jours spéciaux
 *
 */

public class PlanningController {
    private PlanningView view;
    private YearMonth currentYearMonth;


    public PlanningController(PlanningView view) {
        this.view = view;
        // Initialisation sur le mois courant
        currentYearMonth = YearMonth.now();
        initController();
        updateCalendar();
    }


    // Configure les écouteurs d'évènements pour les boutons
    private void initController() {
        view.getPrevButton().addActionListener(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updateCalendar();
        });
        view.getNextButton().addActionListener(e -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            updateCalendar();
        });
    }

    // Met à jour l'affichage : le label et la grille du calendrier
    private void updateCalendar() {
        // Mise à jour du label (nom du mois en français et année)
        String month = currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        int year = currentYearMonth.getYear();
        // On met en majuscule la première lettre du mois
        view.setMonthLabel(month.substring(0, 1).toUpperCase() + month.substring(1) + " " + year);
        // Reconstruit la grille du calendrier
        drawCalendar();
    }


    // Construit la grille du calendrier mensuel
    private void drawCalendar() {
        view.clearCalendarPanel();

        // Ajoute une ligne d’en-tête avec le nom des jours de la semaine
        String[] days = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            view.addToCalendarPanel(dayLabel);
        }

        // Récupère la date du premier jour du mois
        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1 = lundi, 7 = dimanche

        // Ajoute des cases vides si le premier jour n'est pas un lundi
        for (int i = 1; i < dayOfWeek; i++) {
            view.addToCalendarPanel(new JLabel(""));
        }

        // Ajoute chaque jour du mois dans la grille
        int daysInMonth = currentYearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            view.addToCalendarPanel(dayLabel);
        }

        view.refreshCalendarPanel();
    }
}

