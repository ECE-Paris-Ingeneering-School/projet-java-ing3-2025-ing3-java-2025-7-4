package Controller;

import View.ReservationView;
import javax.swing.*;

public class ReservationController {
    private ReservationView view;

    public ReservationController(ReservationView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getValidateButton().addActionListener(e -> {
            int nbAdulte = (Integer) view.getAdultsBox().getSelectedItem();
            int nbEnfant = (Integer) view.getChildrenBox().getSelectedItem();
            int nbBebe = (Integer) view.getBabiesBox().getSelectedItem();



            System.out.println("Adulte(s): " + nbAdulte
                    + ", Enfant(s): " + nbEnfant
                    + ", Bébé(s): " + nbBebe);
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
