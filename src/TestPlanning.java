import Controller.PlanningController;
import View.PlanningView;

import javax.swing.SwingUtilities;

public class TestPlanning {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlanningView view = new PlanningView();
            new PlanningController(view);
            view.setVisible(true);
        });
    }
}
