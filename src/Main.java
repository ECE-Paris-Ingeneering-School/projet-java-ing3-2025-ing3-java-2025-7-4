import DAO.DaoFactory;
import DAO.Reservation.ReservationDAO;
import view.Attraction.AttractionView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");
        ReservationDAO reservationDAO = new ReservationDAO(daoFactory);

        int cleaned = reservationDAO.deletePendingGuestOrdersAndReservations();
        System.out.println("Nettoyage : " + cleaned + " réservations invité non payées supprimées.");

        SwingUtilities.invokeLater(AttractionView::new);
    }
}
