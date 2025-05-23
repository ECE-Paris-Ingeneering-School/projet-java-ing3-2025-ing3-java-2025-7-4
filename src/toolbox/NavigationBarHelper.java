package toolbox;

import Model.Client.ClientModel;
import Model.Reservation.OrdersModel;
import view.Connect.LoginView;
import view.Attraction.AttractionView;
import view.Reservation.PaymentView;

import javax.swing.*;
import java.awt.*;

public class NavigationBarHelper {

    public static void openLoginView(JFrame from) {
        if (from != null) from.dispose();
        SessionManager.logout();
        new LoginView();
    }

    public static void openAttractionView(Window from, ClientModel client) {
        if (from != null) from.dispose();
        SessionManager.setCurrentUser(client);
        new AttractionView();
    }

}
