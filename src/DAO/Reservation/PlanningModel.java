package DAO.Reservation;

import java.time.LocalDate;
import java.util.List;

/**
 * @author erelr
 * <p>
 * Il s'agit du model planning ça servira à ajouter
 * les jours modifié présent dans la Database
 */

public class PlanningModel {

    // C'est l'id du planning dans la Database
    int id;
    // C'est le type du jour. 1 pour jour spécial, 2 pour haute saison et 3 pour basse saison
    int typeDay;
    // date du jour
    LocalDate date;
    private boolean isHighSeason;
    private boolean isSpecialDay;

    public PlanningModel(int id, LocalDate date, boolean isHighSeason, boolean isSpecialDay) {
        this.id = id;
        this.date = date;
        this.isHighSeason = isHighSeason;
        this.isSpecialDay = isSpecialDay;

        // Automatiquement définir le typeDay en fonction des booléens
        if (isSpecialDay) this.typeDay = 1;
        else if (isHighSeason) this.typeDay = 2;
        else this.typeDay = 3;
    }

    //  Constructeur simplifié
    public PlanningModel(int id, boolean isHighSeason, boolean isSpecialDay, LocalDate date) {
        this.id = id;
        this.isHighSeason = isHighSeason;
        this.isSpecialDay = isSpecialDay;
        this.date = date;

        // Type de jour déterminé automatiquement
        if (isSpecialDay) this.typeDay = 1;
        else if (isHighSeason) this.typeDay = 2;
        else this.typeDay = 3;
    }


    public int getId() {
        return id;
    }

    public int getTypeDay() {
        return typeDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isHighSeason() {
        return isHighSeason;
    }

    public boolean isSpecialDay() {
        return isSpecialDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
