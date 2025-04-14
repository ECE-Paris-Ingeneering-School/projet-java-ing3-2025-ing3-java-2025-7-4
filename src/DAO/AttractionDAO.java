// --- AttractionDAO.java refactorisé avec DaoFactory ---
package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.AttractionModel;

public class AttractionDAO {
    private final DaoFactory daoFactory;

    public AttractionDAO(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupère toutes les attractions de la base de données.
     */
    public List<AttractionModel> getAllAttractions() {
        List<AttractionModel> attractions = new ArrayList<>();
        String query = "SELECT * FROM Attraction";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("attraction_id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                String typePers = rs.getString("type_pers");
                String image = rs.getString("image");
                double prix = rs.getDouble("prix");

                attractions.add(new AttractionModel(id, nom, description, typePers, image, prix));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }
    public AttractionModel getAttractionById(int id) {
        AttractionModel resultAttraction = null;

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM attraction WHERE attraction_id = '" + id + "'");

            while (resultSet.next()) {
                int attractionId = resultSet.getInt("attraction_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String personType = resultSet.getString("person_type");
                String imagePath = resultSet.getString("image_path");
                double price = resultSet.getDouble("price");

                if (id == attractionId) {
                    System.out.println("Attraction trouvée dans la base de données");
                    resultAttraction = new AttractionModel(attractionId, name, description, personType, imagePath, price);
                    break;
                }
            }

            statement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération de l'attraction impossible");
        }

        return resultAttraction;
    }


    /**
     * Insère une nouvelle attraction dans la base.
     */
    public boolean insertAttraction(AttractionModel attraction) {
        try {
            Connection connexion = daoFactory.getConnection();

            int attractionID = attraction.getAttractionID();
            String name = attraction.getName();
            String description = attraction.getDescription();
            String persType = attraction.getTypePers();
            String image = attraction.getImage();
            Double price = attraction.getPrix();

            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO attraction(attraction_id, name, description, person_type, image_path, price) VALUES ('" + attractionID + "','" + name + "','" + description + "','" + persType + "','" + image + "','" + price + "')", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                attraction.setOrderId(generatedId);
                System.out.println("Attraction créé avec ID : " + generatedId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'attraction impossible");
            return false;
        }
        return false;
    }


    public boolean updateAttraction(AttractionModel attraction) {
        int rowsAffected = 0;
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            int id = attraction.getAttractionID();
            String name = attraction.getName();
            String description = attraction.getDescription();
            String typePers = attraction.getTypePers();
            String image = attraction.getImage();
            double price = attraction.getPrix();

            String sql = "UPDATE attraction SET " +
                    "name = '" + name + "', " +
                    "description = '" + description + "', " +
                    "person_type = '" + typePers + "', " +
                    "image_path = '" + image + "', " +
                    "price = " + price +
                    " WHERE attraction_id = " + id;

            rowsAffected = statement.executeUpdate(sql);

            statement.close();
            connexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification de l'attraction impossible");
            return false;
        }

        if (rowsAffected > 0) {
            System.out.println("Attraction mise à jour avec succès.");
            return true;
        } else {
            System.out.println("Aucune attraction trouvée avec l'ID spécifié.");
            return false;
        }
    }


    /**
     * Supprime une attraction selon son ID.
     */
    public void deleteAttraction(int id) {
        String query = "DELETE FROM Attraction WHERE attraction_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}