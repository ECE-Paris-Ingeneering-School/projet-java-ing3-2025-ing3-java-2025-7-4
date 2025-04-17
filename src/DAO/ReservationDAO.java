package DAO;

import Model.ReservationModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<ReservationModel> getAllReservations() {
        List<ReservationModel> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ReservationModel r = new ReservationModel(
                        rs.getInt("reservation_id"),
                        rs.getInt("account_id"),
                        rs.getInt("program_id"),
                        rs.getInt("adult_count"),
                        rs.getInt("children_count"),
                        rs.getInt("baby_count"),
                        rs.getDate("dateReservation").toLocalDate()
                );
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<ReservationModel> getReservationsByUserId(int accountId) {
        List<ReservationModel> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE account_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ReservationModel r = new ReservationModel(
                        rs.getInt("reservation_id"),
                        rs.getInt("account_id"),
                        rs.getInt("program_id"),
                        rs.getInt("adult_count"),
                        rs.getInt("children_count"),
                        rs.getInt("baby_count"),
                        rs.getDate("dateReservation").toLocalDate()
                );
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean deleteReservation(int reservationId) {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean deleteReservationById(int id) {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}


