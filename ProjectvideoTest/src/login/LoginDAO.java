package login;

import client.ClientModel;

import java.sql.*;

/**
 * DAO pour gérer l'authentification d'un client à partir de la BDD.
 */
public class LoginDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/ParcAttraction";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    /**
     * Vérifie si un compte existe avec les identifiants fournis.
     * @param email email saisi
     * @param password mot de passe saisi
     * @return un objet ClientModel si trouvé, ou un client invité sinon
     */
    public ClientModel authenticate(String email, String password) {
        String query = "SELECT * FROM Compte WHERE Email = ? AND MDP = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ClientModel(
                        rs.getInt("ID_Compte"),
                        rs.getString("Nom"),
                        rs.getString("Email"),
                        rs.getString("MDP"),
                        rs.getInt("Type_Compte"),
                        rs.getInt("Age")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Si non trouvé → invité par défaut (ID 7)
        return new ClientModel(7, "Invité", "invite@legendaria.com", "", 0, 0);
    }
}
