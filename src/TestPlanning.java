import javax.swing.SwingUtilities;

import Controller.ReservationController;
import View.PlanningView;
import Controller.PlanningController;
import View.ReservationView;

/*
public class TestPlanning {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlanningView view = new PlanningView(1);
            new PlanningController(view);
            view.setVisible(true);
        });
    }
}
*/
public class TestPlanning {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReservationView view = new ReservationView();
            new ReservationController(view);
            view.setVisible(true);
        });
    }
}