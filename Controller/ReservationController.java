package Controller;

import Model.ReservationModel;
import View.ReservationView;
import View.PlanningView;

public class ReservationController {
    private ReservationView view;
    private ReservationModel reservation = new ReservationModel(2, 0) ;

    public ReservationController(ReservationView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getValidateButton().addActionListener(e -> {
            int nbAdulte = (Integer) view.getAdultsBox().getSelectedItem();
            int nbEnfant = (Integer) view.getChildrenBox().getSelectedItem();
            int nbBebe = (Integer) view.getBabiesBox().getSelectedItem();

            reservation.addNumClient(nbAdulte, nbEnfant, nbBebe);

            view.dispose();


            PlanningView planningView = new PlanningView(0);
            new PlanningController(planningView, reservation);
            planningView.setVisible(true);
        });
    }
}
