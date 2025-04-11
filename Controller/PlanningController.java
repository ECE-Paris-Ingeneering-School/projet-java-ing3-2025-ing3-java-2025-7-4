package Controller;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import Model.PlanningModel;
import View.PlanningView;

public class PlanningController {
    private PlanningView view;
    private YearMonth currentYearMonth;
    private List<PlanningModel> specialDays; // Liste des jours spéciaux

    public PlanningController(PlanningView view) {
        this.view = view;
        // Initialisation sur le mois en cours
        currentYearMonth = YearMonth.now();
        specialDays = new ArrayList<>();

        // Exemples de jours spéciaux pour la démonstration :
        // Par exemple, le 10 du mois courant en type 1 (vert), le 15 en type 2 (bleu) et le 20 en type 3 (violet)
        specialDays.add(new PlanningModel(1, 1, currentYearMonth.atDay(10)));
        specialDays.add(new PlanningModel(2, 2, currentYearMonth.atDay(15)));
        specialDays.add(new PlanningModel(3, 3, getDate()));

        initController();
        updatePlanning();
    }

    private LocalDate getDate() {
        return currentYearMonth.atDay(20);
    }

    // Configure les écouteurs d’événements pour les boutons de navigation
    private void initController() {
        view.getPrevButton().addActionListener(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updatePlanning();
        });
        view.getNextButton().addActionListener(e -> {
            currentYearMonth = currentYearMonth.plusMonths(1);
            updatePlanning();
        });
    }

    // Met à jour l'affichage du label et reconstruit le planning
    private void updatePlanning() {
        String month = currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        int year = currentYearMonth.getYear();
        // Mise en majuscule de la première lettre du mois
        view.setMonthLabel(month.substring(0, 1).toUpperCase() + month.substring(1) + " " + year);
        drawPlanning();
    }

    // Construit la grille du planning et colore les jours spéciaux
    private void drawPlanning() {
        view.clearPlanningPanel();

        // Ajoute l'en-tête avec les noms des jours de la semaine
        String[] days = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            view.addToPlanningPanel(dayLabel);
        }

        // Détermine le jour de la semaine pour le premier jour du mois
        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1 = lundi, 7 = dimanche

        // Ajoute des cases vides pour les jours précédant le 1er du mois
        for (int i = 1; i < dayOfWeek; i++) {
            view.addToPlanningPanel(new JLabel(""));
        }

        // Ajoute chaque jour du mois dans la grille
        int daysInMonth = currentYearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentYearMonth.atDay(day);
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            // Vérifie si la date correspond à un jour spécial
            PlanningModel planning = getPlanningByDate(date);
            if (planning != null) {
                // Change la couleur de fond en fonction du type de jour
                switch (planning.getTypeDay()) {
                    case 1:
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(Color.GREEN);
                        break;
                    case 2:
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(Color.BLUE);
                        break;
                    case 3:
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(new Color(238, 130, 238));
                        break;
                }
            }

            view.addToPlanningPanel(dayLabel);
        }

        view.refreshPlanningPanel();
    }

    // Retourne le PlanningModel correspondant à la date donnée (s'il existe)
    private PlanningModel getPlanningByDate(LocalDate date) {
        for (PlanningModel pm : specialDays) {
            if (pm.getDate().equals(date)) {
                return pm;
            }
        }
        return null;
    }
}
