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
import Model.ReservationModel;
import View.PlanningView;
import java.time.format.DateTimeFormatter;

public class PlanningController {
    private PlanningView view;
    private YearMonth currentYearMonth;
    private List<PlanningModel> specialDays; // Liste des jours spéciaux
    private LocalDate lastClickedDate = null;
    private Color lastClickedColor= null;
    private int lastClickedPrice = 0;
    private ReservationModel reservation;
    private JLabel lastClickedDayLabel = null;

    public PlanningController(PlanningView view, ReservationModel reservation) {
        this.reservation = reservation;
        this.view = view;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Initialisation sur le mois en cours
        currentYearMonth = YearMonth.now();
        specialDays = new ArrayList<>();

        // Exemples de jours spéciaux pour la démonstration :
        // Par exemple, le 10 du mois courant en type 1 (vert), le 15 en type 2 (bleu) et le 20 en type 3 (violet)
        specialDays.add(new PlanningModel(1, 1, LocalDate.parse("13/04/2025", formatter)));
        specialDays.add(new PlanningModel(2, 2, LocalDate.parse("15/04/2025", formatter)));
        specialDays.add(new PlanningModel(3, 3,  LocalDate.parse("18/04/2025", formatter)));

        initController();
        updatePlanning();
        initValidateButton1();
        initValidateButtonForRadio();
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

    // Ajout de l'écouteur pour le bouton "Valider"
    private void initValidateButton1() {
        JButton validateButton = view.getValidateButton1();
        if (validateButton != null) {
            validateButton.addActionListener(e -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                if (lastClickedDate != null && lastClickedPrice != 0) {

                    reservation.addReservationDate(lastClickedDate.format(formatter));
                    reservation.addPrice((lastClickedPrice*reservation.getNumAdults())+(lastClickedPrice*reservation.getNumKids()*0.7));

                    System.out.println("Dernière date sélectionnée : "
                            + reservation.getReservationDate()
                            + " - Prix : " + reservation.getPrice());
                } else {
                    System.out.println("Aucune date n'a été sélectionnée.");
                }
            });
        }
    }
    private void initValidateButtonForRadio() {
        JButton validateButton = view.getValidateButton2();
        if (validateButton != null ) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            validateButton.addActionListener(e -> {
                if (lastClickedDate != null) {
                    String selectedOption = "";
                    if (view.getOption1().isSelected()) {
                        selectedOption = view.getOption1().getText();
                    } else if (view.getOption2().isSelected()) {
                        selectedOption = view.getOption2().getText();
                    } else if (view.getOption3().isSelected()) {
                        selectedOption = view.getOption3().getText();
                    }
                    if (!selectedOption.isEmpty()) {
                        System.out.println("Choix sélectionné: " + selectedOption + " Jour sélectionné: " + lastClickedDate.format(formatter));
                    } else {
                        System.out.println("Aucune option n'a été sélectionnée.");
                    }
                }
            });
        }
    }

    // Construit la grille du planning et colore les jours spéciaux
    private void drawPlanning() {
        view.clearPlanningPanel();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
            final LocalDate currentDate = currentYearMonth.atDay(day);
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);

            dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            if (lastClickedDate != null && currentDate.equals(lastClickedDate)) {
                dayLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                // Actualisation de la référence pour la dernière case cliquée (optionnel)
                lastClickedDayLabel = dayLabel;
            } else {
                dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }



            PlanningModel planning = getPlanningByDate(currentDate);
            if (planning != null) {
                // Application de la couleur en fonction de la spécialité
                switch (planning.getTypeDay()) {
                    case 1:
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(new Color(234, 197, 4, 137));
                        break;
                    case 2:
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(new Color(218, 45, 4, 137));
                        break;
                    case 3:
                        dayLabel.setOpaque(true);
                        dayLabel.setBackground(new Color(72, 255, 255));
                        break;
                }
            } else {
                // Jour normal : Couleur en fonction du weekend ou de la semaine
                int dayOfWeekValue = currentDate.getDayOfWeek().getValue();
                dayLabel.setOpaque(true);
                if (dayOfWeekValue == 6 || dayOfWeekValue == 7) { // Weekend
                    dayLabel.setBackground(new Color(218, 45, 4, 137));
                } else { // Semaine
                    dayLabel.setBackground(new Color(234, 197, 4, 137));
                }
            }



            // Stocker la date et la couleur lors d'un clic
            dayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {

                    lastClickedDate = currentDate;
                    lastClickedColor = dayLabel.getBackground();
                    if (lastClickedColor.equals(new Color(234, 197, 4, 137))) {
                        lastClickedPrice = 20;
                    }
                    if (lastClickedColor.equals(new Color(218, 45, 4, 137))) {
                        lastClickedPrice = 25;
                    }
                    if (lastClickedColor.equals(new Color(72, 255, 255))) {
                        lastClickedPrice = 40;
                    }
                    updatePlanning();
                }
            });

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
