package DAO;

import Model.ReservationModel;

import java.util.ArrayList;

/*
 * Interface IReservationDAO
 * @author William
 * Cette interface définit les méthodes pour interagir avec la base de données concernant les réservations.
 * Elle permet de récupérer, sauvegarder, mettre à jour et supprimer des réservations.
 */
public interface IReservationDAO {
    /**
     * Méthode pour récupérer toutes les réservations
     * @return une liste de toutes les réservations
     * @author William
     */
    ArrayList<ReservationModel> getReservAll();

    /**
     * Méthode pour récupérer une réservation par son ID
     * @param id l'identifiant de la réservation
     * @return la réservation correspondant à l'ID
     * @author William
     */
    ReservationModel getReserv(int id);

    /**
     * Méthode pour sauvegarder une nouvelle réservation
     * @param reservation la réservation à sauvegarder
     * @author William
     */
    void saveReserv(ReservationModel reservation);

    /**
     * Méthode pour mettre à jour une réservation existante
     * @param reservation la réservation à mettre à jour
     * @author William
     */
    void updateReserv(ReservationModel reservation);

    /**
     * Méthode pour supprimer une réservation par son ID
     * @param id l'identifiant de la réservation à supprimer
     * @author William
     */
    void deleteReserv(int id);
}
