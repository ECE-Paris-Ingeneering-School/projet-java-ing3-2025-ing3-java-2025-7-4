package Model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author erelr
 *
 * Il s'agit du model planning ça servira à ajouter
 * les jours modifié présent dans la Database
 *
 */

public class PlanningModel {

    // C'est l'id du planning dans la Database
    int id;
    // C'est le type du jour. 1 pour jour spécial, 2 pour haute saison et 3 pour basse saison
    int typeDay;
    // date du jour
    LocalDate date;

    public PlanningModel(int id, int typeDay, LocalDate date) {
        this.id = id;
        this.typeDay = typeDay;
        this.date = date;
    }

    /**
     * @author erelr
     * @param listPlanningModel liste de tout les jours spéciaux
     * @param planningModel jour spécial à ajouter à la liste
     * @return listPlanningModel
     */
    List<PlanningModel> addToPlanningModel(List<PlanningModel> listPlanningModel, PlanningModel planningModel) {
        listPlanningModel.add(planningModel);
        return listPlanningModel;
    }
}
