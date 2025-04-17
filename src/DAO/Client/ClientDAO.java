package DAO.Client;

import DAO.DaoFactory;
import Model.Client.ClientModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private DaoFactory daoFactory;

    public ClientDAO(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<ClientModel> getAllClients() {
        List<ClientModel> clients = new ArrayList<>();
        String sql = "SELECT * FROM account";

        try (Connection conn = daoFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("account_id");
                String firstname = rs.getString("firstname");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                LocalDate birthdate = rs.getDate("birthdate").toLocalDate();
                String email = rs.getString("email");
                String password = rs.getString("password");
                int role = rs.getInt("account_type");

                clients.add(new ClientModel(id, role, firstname, surname, age, birthdate, email, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public boolean toggleClientRole(int clientId, int currentRole) {
        int newRole = (currentRole == 2) ? 1 : 2; // admin <-> member
        String sql = "UPDATE account SET account_type = ? WHERE account_id = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newRole);
            stmt.setInt(2, clientId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteClientById(int id) {
        String sql = "DELETE FROM account WHERE account_id = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}