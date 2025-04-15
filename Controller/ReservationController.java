package Controller;

import Model.ReservationModel;
import View.ReservationView;
import javax.swing.*;

public class ReservationController {
    private ReservationView view;
    private ReservationModel model = new ReservationModel(2, 0.8) ;

    public ReservationController(ReservationView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getValidateButton().addActionListener(e -> {
            int nbAdulte = (Integer) view.getAdultsBox().getSelectedItem();
            int nbEnfant = (Integer) view.getChildrenBox().getSelectedItem();
            int nbBebe = (Integer) view.getBabiesBox().getSelectedItem();

            model.addNumClient(nbAdulte, nbEnfant, nbBebe);

            System.out.println("Adulte(s): " + model.getNumAdults()
                    + ", Enfant(s): " + model.getNumKids()
                    + ", Bébé(s): " + model.getNumBabys());
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservationView view = new ReservationView();
            new ReservationController(view);
            view.setVisible(true);
        });
    }
}
