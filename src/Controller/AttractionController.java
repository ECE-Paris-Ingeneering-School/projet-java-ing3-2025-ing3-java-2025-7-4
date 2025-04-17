package Controller;

import DAO.AttractionDAO;
import DAO.DaoFactory;
import Model.AttractionModel;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour la gestion des attractions.
 * Sert d’intermédiaire entre la vue et la couche DAO.
 *
 * @author William
 * @version 2.0
 * @since 2025
 */
public class AttractionController {

    /**
     * Attribut pour récupérer et communiquer avec le DAO
     */
    private final AttractionDAO dao;

    /**
     * Constructeur du controller
     *
     * @param daoFactory Paramètre contenant les informations pour se connecter à la base de données
     */
    public AttractionController(DaoFactory daoFactory) {
        this.dao = new AttractionDAO(daoFactory);
    }

    /**
     * Récupère toutes les attractions depuis la base de données.
     *
     * @return liste des attractions
     */
    public List<AttractionModel> getAllAttractions() {
        return dao.getAllAttractions();
    }

    /**
     * Crée une nouvelle attraction à partir d'une Map de paramètres.
     *
     * @param prAttraction données de l’attraction
     */
    public void createAttra(Map<String, Object> prAttraction) {
        AttractionModel attraction = new AttractionModel(
                0,
                (String) prAttraction.get("name"),
                (String) prAttraction.get("description"),
                (String) prAttraction.get("person_type"),
                (String) prAttraction.get("image_path"),
                (double) prAttraction.get("price")
        );
        dao.insertAttraction(attraction);
    }

    /**
     * Met à jour une attraction existante à partir d'une Map de paramètres.
     *
     * @param prAttraction données mises à jour
     */
    public void updateAttra(Map<String, Object> prAttraction) {
        AttractionModel attraction = new AttractionModel(
                (int) prAttraction.get("attraction_id"),
                (String) prAttraction.get("name"),
                (String) prAttraction.get("description"),
                (String) prAttraction.get("person_type"),
                (String) prAttraction.get("image_path"),
                (double) prAttraction.get("price")
        );
        dao.updateAttraction(attraction);
    }

    /**
     * Supprime une attraction selon son ID.
     *
     * @param prID identifiant de l’attraction à supprimer
     */
    public void deleteAttra(int prID) {
        dao.deleteAttraction(prID);
    }
}
