package DAO;

import Model.PlanningModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanningDAOImpl {
    private final DaoFactory daoFactory;

    public PlanningDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<PlanningModel> getAllPrograms() {
        List<PlanningModel> programs = new ArrayList<>();
        String sql = "SELECT * FROM program";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("program_id");
                LocalDate date = rs.getDate("program_date").toLocalDate();
                boolean isHighSeason = rs.getBoolean("is_highSeason");
                boolean isSpecialDay = rs.getBoolean("special_day");
                programs.add(new PlanningModel(id, isHighSeason, isSpecialDay, date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return programs;
    }

    public boolean updateProgramType(int programId, boolean isHighSeason, boolean isSpecialDay) {
        String sql = "UPDATE program SET is_highSeason = ?, special_day = ? WHERE program_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isHighSeason);
            stmt.setBoolean(2, isSpecialDay);
            stmt.setInt(3, programId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PlanningModel getProgramByDate(LocalDate date) {
        String sql = "SELECT * FROM program WHERE program_date = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("program_id");
                    boolean isHighSeason = rs.getBoolean("is_highSeason");
                    boolean isSpecialDay = rs.getBoolean("special_day");
                    return new PlanningModel(id, isHighSeason, isSpecialDay, date);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
