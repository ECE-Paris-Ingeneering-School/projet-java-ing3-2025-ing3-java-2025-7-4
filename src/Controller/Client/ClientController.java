package Controller.Client;

import DAO.Client.ClientDAO;
import DAO.DaoFactory;
import Model.Client.ClientModel;

import java.util.List;

public class ClientController {

    private final ClientDAO dao;

    public ClientController(DaoFactory daoFactory) {
        this.dao = new ClientDAO(daoFactory);
    }

    public List<ClientModel> getAllClients() {
        return dao.getAllClients();
    }

    public boolean toggleClientRole(int clientId, int currentRole) {
        return dao.toggleClientRole(clientId, currentRole);
    }

    public boolean deleteClientById(int clientId) {
        return dao.deleteClientById(clientId);
    }

    // Futurs ajouts possibles : mise à jour des infos, vérification email, etc.
}
