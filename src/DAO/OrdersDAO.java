package DAO;

// DAO OrderDAO.java
import Model.OrdersModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

class OrderDAO {
    private DaoFactory daoFactory;

    /**
     * Instancie un daoFactory
     * @param daoFactory
     */
    public OrderDAO(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * crée un nouvelle commande dans la base de donnée
     * @param order
     */
    public void createOrder(OrdersModel order) {
        try{
            Connection connexion= daoFactory.getConnection();

            LocalDateTime rdvFulltime = order.getRdvFulltime();
            int personCount = order.getPersonCount();
            float price = order.getPrice();
            String status = order.getStatus();
            int attractionId = order.getAttractionId();
            int reservationId = order.getReservationId();

            PreparedStatement preparedStatement= connexion.prepareStatement("INSERT INTO orders(rdv_fulltime, person_count, price, status, attraction_id, reservation_id) VALUES ('" + rdvFulltime +"','" + personCount +"','" + price +"','" + status +"','" + attractionId +"','" + reservationId +"')",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                order.setOrderId(generatedId);
                System.out.println("Order créé avec ID : " + generatedId);
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Ajout de la commande impossible");
        }
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        try{
            Connection connexion= daoFactory.getConnection();
            Statement statement= connexion.createStatement();

            String sql="UPDATE orders SET status = '" + newStatus + "' WHERE orderId = " + orderId;
            int rowsAffected= statement.executeUpdate(sql);
            // Vérification que la mise à jour a bien eu lieu
            if (rowsAffected > 0) {
                System.out.println("Produit mis à jour avec succès.");
            } else {
                System.out.println("Aucun produit trouvé avec l'ID spécifié.");
            }

            // Fermeture des ressources
            statement.close();
            connexion.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Modification du produit impossible");
        }

    }
}
