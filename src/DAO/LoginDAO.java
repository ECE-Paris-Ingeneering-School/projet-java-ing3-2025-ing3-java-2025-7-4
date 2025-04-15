package DAO;

import Model.ClientModel;

import java.sql.*;
import java.time.LocalDate;

/**
 * DAO pour gérer l'authentification d'un client à partir de la BDD.
 */
public class LoginDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/attractions_db";
    private static final String USER = "root";

    public ClientModel authenticate(String email, String password) {
        String query = "SELECT * FROM account WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, "");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ClientModel(
                        rs.getInt("account_id"),
                        rs.getInt("account_type"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getDate("birthdate").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retourne un client "invité" en cas d'échec (ID fictif = 7)
        return new ClientModel(
                7,
                0,
                "Invité",
                "",
                0,
                LocalDate.of(1900, 1, 1),
                "invite@legendaria.com",
                ""
        );
    }
}
