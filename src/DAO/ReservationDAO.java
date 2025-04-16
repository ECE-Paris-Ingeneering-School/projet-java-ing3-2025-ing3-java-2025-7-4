package DAO;

import Model.ReservationModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ReservationDAO {
    private final DaoFactory daoFactory;

    public ReservationDAO(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public boolean createReservation(ReservationModel reservation) {
        String sql = "INSERT INTO reservation (adult_count, children_count, baby_count, dateReservation, program_id, account_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reservation.getAdultCount());
            stmt.setInt(2, reservation.getChildrenCount());
            stmt.setInt(3, reservation.getBabyCount());
            stmt.setDate(4, java.sql.Date.valueOf(reservation.getDateReservation()));
            stmt.setInt(5, reservation.getProgramId());
            stmt.setInt(6, reservation.getAccountId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (var rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        reservation.setReservationId(generatedId);  // maj l'objet avec l'ID généré
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

