// ======================= REGISTRATION DAO =======================
package registration;

import java.sql.*;
import java.util.Map;

/**
 * DAO pour enregistrer un nouveau compte client dans la base de données.
 * Vérifie d'abord que l'email n'est pas déjà utilisé.
 */
public class RegistrationDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/ParcAttraction";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    /**
     * Insère un nouveau client si l'email est libre.
     * @param data Map avec name, email, password, age
     * @return true si succès, false si email déjà existant
     */
    public boolean insertClient(Map<String, String> data) {
        String email = data.get("email");

        // Vérifie si l'email existe déjà
        if (emailExists(email)) return false;

        String sql = "INSERT INTO Compte (Nom, Email, MDP, Type_Compte, Age) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data.get("name"));
            stmt.setString(2, data.get("email"));
            stmt.setString(3, data.get("password"));
            stmt.setInt(4, 1); // Par défaut : utilisateur simple
            stmt.setInt(5, Integer.parseInt(data.get("age")));

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Vérifie si un email est déjà utilisé
     */
    private boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Compte WHERE Email = ?";
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
        return true; // Par sécurité, empêche la création en cas d'erreur
    }
}
