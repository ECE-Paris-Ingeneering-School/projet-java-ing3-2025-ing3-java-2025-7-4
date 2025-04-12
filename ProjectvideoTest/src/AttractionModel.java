package attraction;

/**
 * Représente une attraction dans le parc.
 * Contient les informations principales issues de la base de données.
 * Sert de modèle pour le module MVC lié aux attractions.
 *
 * @author
 * @version 1.0
 * @since 2025
 */
public class AttractionModel {

    private int attractionID;
    private String nom;
    private String description;
    private String typePers; // ex : Sensations fortes, Famille, Découverte
    private String video;    // chemin vers le fichier vidéo
    private String image;    // chemin vers l'image

    /**
     * Constructeur de la classe AttractionModel.
     *
     * @param attractionID identifiant unique de l'attraction
     * @param nom nom de l'attraction
     * @param description description de l'attraction
     * @param typePers type de public ciblé (Sensations fortes, Famille, etc.)
     * @param video chemin d'accès au fichier vidéo
     * @param image chemin d'accès à l'image de l'attraction
     */
    public AttractionModel(int attractionID, String nom, String description, String typePers, String video, String image) {
        this.attractionID = attractionID;
        this.nom = nom;
        this.description = description;
        this.typePers = typePers;
        this.video = video;
        this.image = image;
    }

    public int getAttractionID() {
        return attractionID;
    }

    public void setAttractionID(int attractionID) {
        this.attractionID = attractionID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return nom + " [" + typePers + "]";
    }
}
