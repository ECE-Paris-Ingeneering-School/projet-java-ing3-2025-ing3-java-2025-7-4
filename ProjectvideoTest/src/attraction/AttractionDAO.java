package attraction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import attraction.AttractionModel;

/**
 * DAO permettant l'accès à la base de données pour les attractions.
 * Utilise JDBC pour effectuer les opérations de lecture et écriture.
 *
 * Connexion à la base : utilisateur "root", mot de passe "mysql"
 * Base utilisée : ParcAttraction
 *
 * @author
 * @version 1.0
 * @since 2025
 */
public class AttractionDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/ParcAttraction";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    /**
     * Récupère toutes les attractions de la base de données.
     * @return liste des attractions
     */
    public List<AttractionModel> getAllAttractions() {
        List<AttractionModel> attractions = new ArrayList<>();

        String query = "SELECT * FROM Attraction";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("ID_Attraction");
                String nom = rs.getString("Nom");
                String description = rs.getString("Description");
                String typePers = rs.getString("Type_Pers");
                String video = rs.getString("Video");
                String image = rs.getString("Image");
                float prix = rs.getFloat("Prix");

                attractions.add(new AttractionModel(id, nom, description, typePers, video, image, prix));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }

    /**
     * Insère une nouvelle attraction dans la base.
     * @param attraction l'attraction à insérer
     */
    public void insertAttraction(AttractionModel attraction) {
        String query = "INSERT INTO Attraction (Nom, Description, Type_Pers, Video, Image, Prix) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getDescription());
            pstmt.setString(3, attraction.getTypePers());
            pstmt.setString(4, attraction.getVideo());
            pstmt.setString(5, attraction.getImage());
            pstmt.setFloat(6, attraction.getPrix());


            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour une attraction existante.
     * @param attraction l'attraction avec les nouvelles données
     */
    public void updateAttraction(AttractionModel attraction) {
        String query = "UPDATE Attraction SET Nom=?, Description=?, Type_Pers=?, Video=?, Image=?, Prix=? WHERE ID_Attraction=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, attraction.getNom());
            pstmt.setString(2, attraction.getDescription());
            pstmt.setString(3, attraction.getTypePers());
            pstmt.setString(4, attraction.getVideo());
            pstmt.setString(5, attraction.getImage());
            pstmt.setFloat(6, attraction.getPrix());
            pstmt.setInt(7, attraction.getAttractionID());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime une attraction selon son ID.
     * @param id identifiant de l'attraction à supprimer
     */
    public void deleteAttraction(int id) {
        String query = "DELETE FROM Attraction WHERE ID_Attraction = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
