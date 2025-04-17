package Model.Attraction;

/**
 * Représente une attraction dans le parc.
 * Contient les informations principales issues de la base de données.
 * Sert de modèle pour le module MVC lié aux attractions.
 *
 * @author
 * @version 1.1
 * @since 2025
 */
public class AttractionModel {

    private int attractionID;
    private String name;
    private String description;
    private String typePers; // ex : Sensations fortes, Famille, Découverte
    private String image;    // chemin vers l'image de l'attraction
    private double prix;      // prix du billet pour l'attraction

    /**
     * Constructeur de la classe AttractionModel.
     *
     * @param attractionID identifiant unique de l'attraction
     * @param name         nom de l'attraction
     * @param description  description de l'attraction
     * @param typePers     type de public ciblé (Sensations fortes, Famille, etc.)
     * @param image        chemin d'accès à l'image
     * @param prix         prix de l'attraction
     */
    public AttractionModel(int attractionID, String name, String description, String typePers, String image, double prix) {
        this.attractionID = attractionID;
        this.name = name;
        this.description = description;
        this.typePers = typePers;
        this.image = image;
        this.prix = prix;
    }

    public int getAttractionID() {
        return attractionID;
    }

    public void setAttractionID(int attractionID) {
        this.attractionID = attractionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypePers() {
        return typePers;
    }

    public void setTypePers(String typePers) {
        this.typePers = typePers;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return name + " [" + typePers + "] - " + prix + " €";
    }

    public void setOrderId(int generatedId) {
        this.attractionID = generatedId;
    }
}
