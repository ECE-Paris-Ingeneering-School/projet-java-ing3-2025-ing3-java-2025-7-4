package Model;

import java.time.LocalDateTime;

/**
 *
 */
public class OrdersModel {
    /**
     *
     */
    private int orderId;
    private LocalDateTime rdvFulltime;
    private int personCount;
    private float price;
    private String status; // "Paid", "Pending", "Cancelled"
    private int attractionId;
    private int reservationId;

    /**
     * Constructeur de la classe
     * @param orderId
     * @param rdvFulltime
     * @param personCount
     * @param price
     * @param status
     * @param attractionId
     * @param reservationId
     */
    public void Orders(int orderId, LocalDateTime rdvFulltime, int personCount, float price,
                       String status, int attractionId, int reservationId) {
        this.orderId = orderId;
        this.rdvFulltime = rdvFulltime;
        this.personCount = personCount;
        this.price = price;
        this.status = status;
        this.attractionId = attractionId;
        this.reservationId = reservationId;

    }

    /**
     * Méthode pour récupèrer l'id de la méthode
     * @return orderID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Getter du rdv
     * @return rdvFullTime
     */
    public LocalDateTime getRdvFulltime() {
        return rdvFulltime;
    }

    /**
     * Getter du nombre de personnes
     * @return personCount
     */
    public int getPersonCount() {
        return personCount;
    }

    /**
     *
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @return
     */
    public int getAttractionId() {
        return attractionId;
    }

    /**
     *
     * @return
     */
    public int getReservationId() {
        return reservationId;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @param orderId
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

