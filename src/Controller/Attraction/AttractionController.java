package Controller.Attraction;

import DAO.Attraction.AttractionDAO;
import DAO.DaoFactory;
import Model.Attraction.AttractionModel;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur pour la gestion des attractions.
 * Sert d’intermédiaire entre la vue et la couche DAO.
 */
public class AttractionController {

    private final AttractionDAO dao;

    /**
     * Crée une instance de AttractionDAO (l’objet qui interagit avec la base de données pour les attractions). Et la stocke dans un attribut dao pour qu’on puisse l’utiliser plus tard dans d’autres méthodes du contrôleur (getAllAttractions, addAttraction, etc.).
     * @param daoFactory est un objet qui permet de créer et configurer les accès à la base de données (DAO)
     */
    public AttractionController(DaoFactory daoFactory) {
        this.dao = new AttractionDAO(daoFactory);
    }

    /**
     *
     * @param map est une structure clé-valeur contenant les données d'une attraction
     * @return un objet AttractionModel construit à partir des valeurs contenues dans cette map.
     */

    private AttractionModel buildAttractionFromMap(Map<String, Object> map) {
        return new AttractionModel(
                (int) map.getOrDefault("attraction_id", 0),
                (String) map.get("name"),
                (String) map.get("description"),
                (String) map.get("person_type"),
                (String) map.get("image_path"),
                Double.parseDouble(map.get("price").toString())
        );
    }

    /**
     *
     * @return retourne une liste d'attractions.
     */

        public List<AttractionModel> getAllAttractions() {
            return dao.getAllAttractions();
        }

    /**
     *
     * @param prAttraction représente un ensemble de données sous forme de paires clé-valeur
     */
    public void createAttra(Map<String, Object> prAttraction) {
        dao.insertAttraction(buildAttractionFromMap(prAttraction));
    }

    /**
     *
     * @param prAttraction ensemble de données sous forme de paires clé-valeur représentant une attraction à mettre à jour.
     */

    public void updateAttra(Map<String, Object> prAttraction) {
        dao.updateAttraction(buildAttractionFromMap(prAttraction));
    }

    /**
     *
     * @param prID L'identifiant de l'attraction à supprimer.
     */

    public void deleteAttra(int prID) {
        dao.deleteAttraction(prID);
    }
}
