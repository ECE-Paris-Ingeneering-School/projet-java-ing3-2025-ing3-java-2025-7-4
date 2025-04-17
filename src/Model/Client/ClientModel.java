package Model.Client;

//Import nécessaires
import java.time.LocalDate;

/**
 * Représente un client dans le système de réservation du parc d'attractions.
 * Contient les informations personnelles nécessaires à la gestion des comptes et des réservations.
 * Utilisée dans le module MVC lié aux clients.
 *
 * @author
 * @version 1.0
 * @since 2025
 */
public class ClientModel {
    private int id;
    private int accountType;  // "guest", "admin", "member"
    private String firstname;
    private String surname;
    private int age;
    private LocalDate birthdate;
    private String email;
    private String password;

    public ClientModel(int id, int accountType, String firstname, String surname,
                       int age, LocalDate birthdate, String email, String password) {
        this.id = id;
        this.accountType = accountType;
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return firstname + " " + surname;
    }

    @Override
    public String toString() {
        return firstname + " " + surname + " (" + accountType + ")";
    }
}