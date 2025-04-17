package DAO.Connect;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

/**
 * DAO pour enregistrer un nouveau compte client dans la base de données.
 */
public class RegistrationDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/attractions_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Insère un nouveau client si l'email n'est pas déjà utilisé.
     *
     * @param data Map avec firstname, surname, email, password, age, birthdate
     * @return true si succès, false si email déjà existant
     */
    public boolean insertClient(Map<String, String> data) {
        String email = data.get("email");

        if (emailExists(email)) return false;

        String sql = "INSERT INTO account (firstname, surname, age, birthdate, email, password, account_type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.get("firstname"));
            stmt.setString(2, data.get("surname"));
            stmt.setInt(3, Integer.parseInt(data.get("age")));

            LocalDate birthdate = LocalDate.parse(data.get("birthdate")); // ⚠️ Format attendu : yyyy-MM-dd
            stmt.setDate(4, Date.valueOf(birthdate));

            stmt.setString(5, data.get("email"));
            stmt.setString(6, data.get("password"));
            stmt.setInt(7, 1); // rôle par défaut

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Erreur lors de l'insertion : " + ex.getMessage());
        }

        return false;
    }

    /**
     * Vérifie si un email est déjà utilisé
     */
    private boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM account WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
