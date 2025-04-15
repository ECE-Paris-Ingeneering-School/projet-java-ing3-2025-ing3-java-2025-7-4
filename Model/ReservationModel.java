package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author erelr
 *
 * Il s'agit du model reservation ça servira à ajouter
 * toutes les données d'une nouvelle réservation
 *
 */

public class ReservationModel {
    private int clientID;
    private List<Integer> attractionID = new ArrayList<>();
    private List<Integer> numTickets = new ArrayList<>();
    private int numAdults;
    private int numKids;
    private int numBabys;
    private String reservationDate;
    private double price = 0;
    private double discount;
    private double typeAcount;

    public ReservationModel(int clientID, int typeAcount) {
        this.clientID = clientID;
        this.typeAcount = typeAcount;
        if (typeAcount == 1) {
            this.discount = 0.8;
        }else {
            this.discount = 1;
        }

    }

    //Fonction pour ajouter le nombre de participant.
    public void addNumClient(int numAdults, int numKids, int numBabys) {
        this.numAdults = numAdults;
        this.numKids = numKids;
        this.numBabys = numBabys;
    }

    //Fonction pour ajouter une attraction et le nombre de ticket réservé.
    public void addAttranction(int attractionID, int numTickets) {
        this.attractionID.add(attractionID);
        this.numTickets.add(numTickets);
    }

    //Fonction pour ajouter la date de reservation.
    public void addReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    //Fonction pour ajouter le prix de chaque attraction au prix total.
    public void addPrice(double price) {
        price = price*this.discount;
        this.price += price;
    }


    public int getClientID() {
        return clientID;
    }

    public int getNumAdults() {
        return numAdults;
    }
    public int getNumKids() {
        return numKids;
    }
    public int getNumBabys() {
        return numBabys;
    }
    public String getReservationDate() {
        return reservationDate;
    }
    public double getPrice() {
        return price;
    }
    public List<Integer> getAttractionID() {
        return attractionID;
    }
    public List<Integer> getNumTickets() {
        return numTickets;
    }

}
