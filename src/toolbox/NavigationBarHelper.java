package toolbox;

import Model.Client.ClientModel;
import Model.Reservation.OrdersModel;
import view.Connect.LoginView;
import view.Attraction.AttractionView;
import view.Reservation.PaymentView;

import javax.swing.*;

public class NavigationBarHelper {

    public static void openLoginView(JFrame from) {
        if (from != null) from.dispose();
        SessionManager.logout();
        new LoginView();
    }

    public static void openAttractionView(JFrame from, ClientModel client) {
        if (from != null) from.dispose();
        SessionManager.setCurrentUser(client);
        new AttractionView();
    }

    public static void openPaymentView(JFrame from, OrdersModel order) {
        if (from != null) from.dispose();
        new PaymentView(order); // on modifie la classe tout de suite après pour virer les params inutiles
    }
}
