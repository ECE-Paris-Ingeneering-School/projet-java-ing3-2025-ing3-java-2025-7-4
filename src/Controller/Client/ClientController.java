/**
 * ClientDAO : objet d’accès à la base de données (DAO = Data Access Object),
 * DaoFactory : fabrique qui fournit des DAO (ex: pour Client, Produit, etc.),
 * ClientModel : le modèle représentant un client (entité métier),
 * List : collection Java pour stocker plusieurs clients.
 */
package Controller.Client;
import DAO.Client.ClientDAO;
import DAO.DaoFactory;
import Model.Client.ClientModel;

import java.util.List;

/**
 * Création de la classe Clientcontroller permettant d'utiliser les méthodes pour accéder aux données client dans la BDD
 */
public class ClientController {

    private final ClientDAO dao;

    /**
     *
     * @param daoFactory permet de générer les DAO nécessaires
     */
    public ClientController(DaoFactory daoFactory) {
        this.dao = new ClientDAO(daoFactory);
    }

    /**
     *
     * @return une liste d'objets ClientModel, donc tous les clients de la base.
     */
    public List<ClientModel> getAllClients() {
        return dao.getAllClients();
    }

    /**
     * Méthode qui permet de changer le rôle (ex: utilisateur → admin ou inversement) d’un client en fonction de son id et de son rôle actuel.
     * @param clientId identienfiant du client
     * @param currentRole Le rôle actuel du client.
     * Cela peut représenter, par exemple :
     * 0 : Utilisateur classique
     * 1 : Administrateur
     * @return Elle retourne un booléen (true ou false), selon que l’opération : a réussi (true) ou échoué (false), par exemple si le clientId n’existe pas ou si la requête SQL a échoué.
     */
    public boolean toggleClientRole(int clientId, int currentRole) {
        return dao.toggleClientRole(clientId, currentRole);
    }

    /**
     * Supprime un client en base de données grâce à son id.
     * @param clientId identifiant du client
     * @return : Retourne un booléen indiquant si la suppression s’est bien passée.
     */

    public boolean deleteClientById(int clientId) {
        return dao.deleteClientById(clientId);
    }

    // Futurs ajouts possibles : mise à jour des infos, vérification email, etc.
}
