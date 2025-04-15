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

            System.out.println("Adulte(s): " + reservation.getNumAdults()
                    + ", Enfant(s): " + reservation.getNumKids()
                    + ", Bébé(s): " + reservation.getNumBabys());

            // Fermez ou cachez la fenêtre de réservation
            view.dispose(); // ou view.setVisible(false);

            // Créez la page du planning (vous pouvez passer 0 ou un autre paramètre selon votre logique)
            PlanningView planningView = new PlanningView(0);
            // Créez le contrôleur correspondant
            new PlanningController(planningView, reservation);
            // Affichez la nouvelle fenêtre
            planningView.setVisible(true);
        });
    }
}
