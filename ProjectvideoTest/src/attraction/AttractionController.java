package attraction;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour la gestion des attractions.
 * Sert d’intermédiaire entre la vue et la couche DAO.
 *
 * @author
 * @version 1.0
 * @since 2025
 */
public class AttractionController {

    private final AttractionDAO dao;

    public AttractionController() {
        this.dao = new AttractionDAO();
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
                (String) prAttraction.get("nom"),
                (String) prAttraction.get("description"),
                (String) prAttraction.get("typePers"),
                (String) prAttraction.get("video"),
                (String) prAttraction.get("image"),
                (float) prAttraction.get("prix")
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
                (int) prAttraction.get("id"),
                (String) prAttraction.get("nom"),
                (String) prAttraction.get("description"),
                (String) prAttraction.get("typePers"),
                (String) prAttraction.get("video"),
                (String) prAttraction.get("image"),
                (float) prAttraction.get("prix")
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
