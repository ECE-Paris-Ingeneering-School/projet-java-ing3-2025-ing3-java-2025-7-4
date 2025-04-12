import DAO.DaoFactory;
import DAO.OrdersDAOImpl;
import Model.OrdersModel;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        //ligne de code à changer en fonction des paramètres
        DaoFactory dao = DaoFactory.getInstance("attractions_db", "root", "");
        System.out.println(dao);
        OrdersDAOImpl daoImpl = new OrdersDAOImpl(dao);

        OrdersModel newOrder = new OrdersModel(
                4, // id auto-généré
                LocalDateTime.now().plusDays(1),
                2, // 2 personnes
                35.50f,
                "Pending",
                1, // id attraction
                42 // id réservation fictive
        );

        daoImpl.createOrder(newOrder);

        daoImpl.updateOrderStatus(newOrder.getOrderId(), "Paid");



    }
}
