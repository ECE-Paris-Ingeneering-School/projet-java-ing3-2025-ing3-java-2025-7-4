package Controller.Reservation;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

import Controller.Payement.PaymentController;
import DAO.*;
import DAO.Attraction.AttractionDAO;
import DAO.Reservation.OrdersDAOImpl;
import DAO.Reservation.PlanningDAOImpl;
import DAO.Reservation.ReservationDAO;
import Model.Attraction.AttractionModel;
import Model.Reservation.OrdersModel;
import Model.Reservation.PlanningModel;
import Model.Reservation.ReservationModel;
import view.Reservation.PlanningView;
import view.Reservation.PaymentView;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class PlanningController {
    private PlanningView view;
    private YearMonth currentYearMonth;
    private List<PlanningModel> specialDays; // Liste des jours spéciaux
    private LocalDate lastClickedDate = null;
    private Color lastClickedColor = null;
    private int lastClickedPrice = 0;
    private ReservationModel reservation;
    private ReservationDAO reservationDAO;
    private JLabel lastClickedDayLabel = null;

    public PlanningController(PlanningView view, ReservationModel reservation) {
        this.reservation = reservation;
        this.view = view;
        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        reservationDAO = new ReservationDAO(daoFactory);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // Initialisation sur le mois en cours
        currentYearMonth = YearMonth.now();
        specialDays = new ArrayList<>();

        // Exemples de jours spéciaux pour la démonstration :
        // Par exemple, le 10 du mois courant en type 1 (vert), le 15 en type 2 (bleu) et le 20 en type 3 (violet)
        PlanningDAOImpl planningDAO = new PlanningDAOImpl(daoFactory);
        specialDays = planningDAO.getAllPrograms();

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
                    reservation.addPrice((lastClickedPrice * reservation.getAdultCount()) + (lastClickedPrice * reservation.getChildrenCount() * 0.7));
                    boolean inserted = reservationDAO.createReservation(reservation);
                    if (inserted) {
                        System.out.println("Réservation insérée avec succès. ID = " + reservation.getReservationId());
                        AttractionDAO attractionDAO = new AttractionDAO(DaoFactory.getInstance("attractions_db", "root", ""));
                        OrdersDAOImpl ordersDAO = new OrdersDAOImpl(DaoFactory.getInstance("attractions_db", "root", ""));
                        PaymentController controller = new PaymentController(ordersDAO);

                        List<AttractionModel> attractions = attractionDAO.getAllAttractions();

                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                        panel.add(new JLabel("Sélectionnez les attractions désirées :"));

                        List<JCheckBox> checkBoxes = new ArrayList<>();
                        for (AttractionModel attraction : attractions) {
                            JCheckBox checkBox = new JCheckBox(attraction.getName() + " (" + attraction.getPrix() + "€)");
                            checkBoxes.add(checkBox);
                            panel.add(checkBox);
                        }

                        int result = JOptionPane.showConfirmDialog(view, panel, "Choix des attractions",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            List<OrdersModel> commandes = new ArrayList<>();
                            LocalDateTime now = LocalDateTime.now();
                            int personCount = reservation.getAdultCount() + reservation.getChildrenCount(); // bébés gratuits

                            for (int i = 0; i < checkBoxes.size(); i++) {
                                if (checkBoxes.get(i).isSelected()) {
                                    AttractionModel attr = attractions.get(i);
                                    float total = (float) (reservation.getPrice() + attr.getPrix());
                                    OrdersModel order = new OrdersModel(
                                            0, now, personCount, total, "Pending",
                                            attr.getAttractionID(), reservation.getReservationId()
                                    );
                                    ordersDAO.createOrder(order);
                                    commandes.add(order);
                                }
                            }

                            if (!commandes.isEmpty()) {
                                new PaymentView(commandes.get(0));
                                view.dispose();
                            } else {
                                JOptionPane.showMessageDialog(view, "Aucune attraction sélectionnée. Réservation seule enregistrée.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "Erreur lors de l'enregistrement de la réservation.");
                        return;
                    }

                    System.out.println("Dernière date sélectionnée : "
                            + reservation.getDateReservation()
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
                        // Appel DAO
                        PlanningDAOImpl planningDAO = new PlanningDAOImpl(DaoFactory.getInstance("attractions_db", "root", ""));
                        PlanningModel program = planningDAO.getProgramByDate(lastClickedDate);

                        if (program != null) {
                            boolean highSeason = view.getOption2().isSelected();
                            boolean specialDay = view.getOption1().isSelected();

                            boolean ok = planningDAO.updateProgramType(program.getId(), highSeason, specialDay);
                            if (ok) {
                                JOptionPane.showMessageDialog(view, "Le jour a été mis à jour !");
                                //updatePlanning();
                                view.dispose();  // Ferme la vue actuelle
                                new PlanningView(1);
                            } else {
                                JOptionPane.showMessageDialog(view, "Erreur lors de la mise à jour.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(view, "Aucun jour trouvé dans la BDD pour cette date.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "Veuillez sélectionner un type de jour.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Veuillez d'abord cliquer sur un jour.");
                }
            });
        }
    }


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
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue(); // 1 = lundi

        for (int i = 1; i < dayOfWeek; i++) {
            view.addToPlanningPanel(new JLabel(""));
        }

        int daysInMonth = currentYearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            final LocalDate currentDate = currentYearMonth.atDay(day);
            JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

            if (lastClickedDate != null && currentDate.equals(lastClickedDate)) {
                dayLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                lastClickedDayLabel = dayLabel;
            }

            // Couleur en fonction des attributs en BDD
            PlanningModel planning = getPlanningByDate(currentDate);
            dayLabel.setOpaque(true);
            if (planning != null) {
                if (planning.isSpecialDay()) {
                    dayLabel.setBackground(new Color(72, 255, 255)); // Violet - Jour spécial
                } else if (planning.isHighSeason()) {
                    dayLabel.setBackground(new Color(218, 45, 4, 137)); // Rouge - Haute saison
                } else {
                    dayLabel.setBackground(new Color(234, 197, 4, 137)); // Jaune - Basse saison
                }
            } else {
                int dayOfWeekValue = currentDate.getDayOfWeek().getValue();
                if (dayOfWeekValue == 6 || dayOfWeekValue == 7) {
                    dayLabel.setBackground(new Color(218, 45, 4, 137)); // Week-end = haute saison par défaut
                } else {
                    dayLabel.setBackground(new Color(234, 197, 4, 137)); // Semaine = basse saison par défaut
                }
            }

            // Gestion du clic sur le jour
            dayLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    lastClickedDate = currentDate;
                    lastClickedColor = dayLabel.getBackground();
                    if (lastClickedColor.equals(new Color(234, 197, 4, 137))) {
                        lastClickedPrice = 20;
                    } else if (lastClickedColor.equals(new Color(218, 45, 4, 137))) {
                        lastClickedPrice = 25;
                    } else if (lastClickedColor.equals(new Color(72, 255, 255))) {
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