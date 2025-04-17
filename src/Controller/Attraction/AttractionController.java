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

    public AttractionController(DaoFactory daoFactory) {
        this.dao = new AttractionDAO(daoFactory);
    }

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

    public List<AttractionModel> getAllAttractions() {
        return dao.getAllAttractions();
    }

    public void createAttra(Map<String, Object> prAttraction) {
        dao.insertAttraction(buildAttractionFromMap(prAttraction));
    }

    public void updateAttra(Map<String, Object> prAttraction) {
        dao.updateAttraction(buildAttractionFromMap(prAttraction));
    }

    public void deleteAttra(int prID) {
        dao.deleteAttraction(prID);
    }
}
