import javax.swing.SwingUtilities;
import View.PlanningView;
import Controller.PlanningController;

public class TestPlanning {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlanningView view = new PlanningView();
            new PlanningController(view);
            view.setVisible(true);
        });
    }
}
