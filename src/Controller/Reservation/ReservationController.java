package Controller.Reservation;

import Model.Client.ClientModel;
import Model.Reservation.ReservationModel;
import toolbox.SessionManager;
import view.Reservation.PlanningView;
import view.Reservation.ReservationView;

public class ReservationController {
    private final ReservationView view;

    public ReservationController(ReservationView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getValidateButton().addActionListener(e -> {
            ClientModel user = SessionManager.getCurrentUser();
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
                    null// pas encore de date choisie
                    , 0
            );

            view.dispose();
            PlanningView planningView = new PlanningView(0);
            new PlanningController(planningView, reservation);
            planningView.setVisible(true);
        });
    }
}
