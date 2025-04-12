package DAO;

// DAO OrderDAO.java
import Model.OrdersModel;

import java.sql.*;
import java.time.LocalDateTime;

public class OrdersDAOImpl {
    private DaoFactory daoFactory;

    /**
     * Instancie un daoFactory
     * @param daoFactory
     */
    public OrdersDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * crée un nouvelle commande dans la base de donnée
     * @param order
     */
    public boolean createOrder(OrdersModel order) {
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
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Ajout de la commande impossible");
            return false;
        }
        return false;
    }
    public OrdersModel getOrderById(int prOrderId) {
        OrdersModel resultOrder = null;
        try{
            Connection connexion= daoFactory.getConnection();
            Statement statement= connexion.createStatement();

            ResultSet resultSet= statement.executeQuery("SELECT * FROM orders WHERE order_id = '" + prOrderId + "'");
            while (resultSet.next()) {
                int orderID = resultSet.getInt("order_id");
                LocalDateTime rdvFulltime = resultSet.getTimestamp("rdv_fulltime").toLocalDateTime();
                int personCount = resultSet.getInt("person_count");
                float price = resultSet.getFloat("price");
                String status = resultSet.getString("status");
                int attractionId = resultSet.getInt("attraction_id");
                int reservationId = resultSet.getInt("reservation_id");

                if(prOrderId==orderID){
                    System.out.println("Commande trouvée dans la base de données");
                    resultOrder=new OrdersModel(orderID,rdvFulltime,personCount,price,status,attractionId,reservationId);
                    break;
                }

            }

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Ajout de la commande impossible");
        }
        return resultOrder;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        int rowsAffected=0;
        try{
            Connection connexion= daoFactory.getConnection();
            Statement statement= connexion.createStatement();

            String sql="UPDATE orders SET status = '" + newStatus + "' WHERE order_id = " + orderId;
            rowsAffected= statement.executeUpdate(sql);
            // Vérification que la mise à jour a bien eu lieu


            // Fermeture des ressources
            statement.close();
            connexion.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Modification du produit impossible");
        }
        if (rowsAffected > 0) {
            System.out.println("Produit mis à jour avec succès.");
            return true;
        } else {
            System.out.println("Aucun produit trouvé avec l'ID spécifié.");
            return false;
        }
    }
}
