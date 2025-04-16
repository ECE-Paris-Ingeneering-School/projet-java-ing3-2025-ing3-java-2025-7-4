package Controller;

import DAO.DaoFactory;
import Model.ClientModel;
import Model.ReservationModel;
import toolbox.SessionManager;
import view.PlanningView;
import view.ReservationView;

import javax.swing.*;

public class ReservationController {
    private final ReservationView view;

    public ReservationController(ReservationView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getValidateButton().addActionListener(e -> {
            ClientModel user = SessionManager.getCurrentUser();
            if (user == null) {
                JOptionPane.showMessageDialog(view, "Vous devez être connecté pour effectuer une réservation.");
                return;
            }

            int nbAdulte = (Integer) view.getAdultsBox().getSelectedItem();
            int nbEnfant = (Integer) view.getChildrenBox().getSelectedItem();
            int nbBebe = (Integer) view.getBabiesBox().getSelectedItem();
            int accountId = user.getId();
            int programId = 1; // valeur temporaire

            ReservationModel reservation = new ReservationModel(
                    0, // id fictif (sera généré)
                    accountId,
                    programId,
                    nbAdulte,
                    nbEnfant,
                    nbBebe,
                    null // pas encore de date choisie
            );

            view.dispose();
            PlanningView planningView = new PlanningView(0);
            new PlanningController(planningView, reservation);
            planningView.setVisible(true);
        });
    }
}
