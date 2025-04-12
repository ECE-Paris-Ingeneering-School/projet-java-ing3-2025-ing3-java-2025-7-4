package client;

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

    private int clientID;
    private String name;
    private String email;
    private String password;
    private int role; // 0 = invité, 1 = utilisateur, 2 = administrateur
    private int age;

    /**
     * Constructeur de la classe ClientModel.
     *
     * @param clientID identifiant unique du client
     * @param name nom complet du client
     * @param email adresse email du client
     * @param password mot de passe du client
     * @param role type de compte (0 = invité, 1 = utilisateur, 2 = administrateur)
     * @param age âge du client
     */
    public ClientModel(int clientID, String name, String email, String password, int role, int age) {
        this.clientID = clientID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.age = age;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}
