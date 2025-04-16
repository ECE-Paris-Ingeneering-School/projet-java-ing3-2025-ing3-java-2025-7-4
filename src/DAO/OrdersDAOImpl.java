package DAO;

// DAO OrderDAO.java

import Model.OrdersModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersDAOImpl {
    private DaoFactory daoFactory;

    /**
     * Instancie un daoFactory
     *
     * @param daoFactory
     */
    public OrdersDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * crée un nouvelle commande dans la base de donnée
     *
     * @param order
     */
    public boolean createOrder(OrdersModel order) {
        try {
            Connection connexion = daoFactory.getConnection();

            LocalDateTime rdvFulltime = order.getRdvFulltime();
            int personCount = order.getPersonCount();
            float price = order.getPrice();
            String status = order.getStatus();
            int attractionId = order.getAttractionId();
            int reservationId = order.getReservationId();

            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO orders(rdv_fulltime, person_count, price, status, attraction_id, reservation_id) VALUES ('" + rdvFulltime + "','" + personCount + "','" + price + "','" + status + "','" + attractionId + "','" + reservationId + "')", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                order.setOrderId(generatedId);
                System.out.println("Order créé avec ID : " + generatedId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de la commande impossible");
            return false;
        }
        return false;
    }

    /**
     * Récupère à l'aide d'un call au DAO toutes les commandes
     * @return list , liste de toutes les commandes
     */
    public List<OrdersModel> getAllOrders() {
        List<OrdersModel> list = new ArrayList<>();
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {
                OrdersModel order = new OrdersModel(resultSet.getInt("order_id"),
                        resultSet.getTimestamp("rdv_fulltime").toLocalDateTime(),
                        resultSet.getInt("person_count"),
                        resultSet.getFloat("price"),
                        resultSet.getString("status"),
                        resultSet.getInt("attraction_id"),
                        resultSet.getInt("reservation_id"));
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération des commandes impossible");
        }
        return list;
    }
    public Map<String, Integer> getStatusCount() {
        Map<String, Integer> statusMap = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as total FROM Orders GROUP BY status";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("total");
                statusMap.put(status, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statusMap;
    }
    public Map<String, Float> getRevenueByAttraction() {
        Map<String, Float> revenueMap = new HashMap<>();
        String sql = "SELECT a.name AS attraction_name, SUM(o.price) AS revenue " +
                "FROM Orders o " +
                "JOIN Attraction a ON o.attraction_id = a.attraction_id " +
                "WHERE o.status = 'Paid' " +
                "GROUP BY a.name";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("attraction_name");
                float revenue = rs.getFloat("revenue");
                revenueMap.put(name, revenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueMap;
    }


    public OrdersModel getOrderById(int prOrderId) {
        OrdersModel resultOrder = null;
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders WHERE order_id = '" + prOrderId + "'");
            while (resultSet.next()) {
                int orderID = resultSet.getInt("order_id");
                LocalDateTime rdvFulltime = resultSet.getTimestamp("rdv_fulltime").toLocalDateTime();
                int personCount = resultSet.getInt("person_count");
                float price = resultSet.getFloat("price");
                String status = resultSet.getString("status");
                int attractionId = resultSet.getInt("attraction_id");
                int reservationId = resultSet.getInt("reservation_id");

                if (prOrderId == orderID) {
                    System.out.println("Commande trouvée dans la base de données");
                    resultOrder = new OrdersModel(orderID, rdvFulltime, personCount, price, status, attractionId, reservationId);
                    break;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de la commande impossible");
        }
        return resultOrder;
    }
    public List<OrdersModel> getOrdersByUser(int accountId) {
        List<OrdersModel> list = new ArrayList<>();
        String sql = "SELECT * FROM orders o JOIN reservation r ON o.reservation_id = r.reservation_id WHERE r.account_id = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrdersModel order = new OrdersModel(
                        rs.getInt("order_id"),
                        rs.getTimestamp("rdv_fulltime").toLocalDateTime(),
                        rs.getInt("person_count"),
                        rs.getFloat("price"),
                        rs.getString("status"),
                        rs.getInt("attraction_id"),
                        rs.getInt("reservation_id")
                );
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        int rowsAffected = 0;
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            String sql = "UPDATE orders SET status = '" + newStatus + "' WHERE order_id = " + orderId;
            rowsAffected = statement.executeUpdate(sql);
            // Vérification que la mise à jour a bien eu lieu


            // Fermeture des ressources
            statement.close();
            connexion.close();
        } catch (SQLException e) {
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
