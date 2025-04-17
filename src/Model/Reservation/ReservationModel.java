package Model.Reservation;

import java.time.LocalDate;

public class ReservationModel {
    private int reservationId;
    private int accountId;
    private int programId;
    private int adultCount;
    private int childrenCount;
    private int babyCount;
    private LocalDate dateReservation;
    private double price;

    public ReservationModel(int reservationId,int accountId, int programId, int adultCount, int childrenCount, int babyCount, LocalDate dateReservation) {
        this.reservationId=reservationId;
        this.accountId = accountId;
        this.programId = programId;
        this.adultCount = adultCount;
        this.childrenCount = childrenCount;
        this.babyCount = babyCount;
        this.dateReservation = dateReservation;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getProgramId() {
        return programId;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    public int getBabyCount() {
        return babyCount;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void addReservationDate(String reservationDate) {
        this.dateReservation = LocalDate.parse(reservationDate, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void addPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return this.price;
    }
}
