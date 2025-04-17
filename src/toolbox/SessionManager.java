package toolbox;//Import des packages nécessaires
import Model.Client.ClientModel;

/**
 * Classe qui va gérer la connection ou non d'un utilisateur
 */
public class SessionManager {
    /**
     * Objet du modèle client contenant ces informations
     */
    private static ClientModel currentUser;

    /**
     * Constructeur de la classe
     * @param user , utilisateur qui est connecté à la page
     */
    public static void setCurrentUser(ClientModel user) {
        currentUser = user;
    }

    /**
     * Getter de l'attribut privé
     * @return currentUser, l'objet contenant ces informations
     */
    public static ClientModel getCurrentUser() {
        return currentUser;
    }

    /**
     * Méthode pour savoir si on est connecté ou invité ou autre
     * @return true ou false
     */
    public static boolean isLoggedIn() {
        return currentUser != null && currentUser.getId() != 7;
    }

    /**
     * Permet de savoir si l'utilisateur actuel est un admin
     * @return true ou false
     */
    public static boolean isAdmin() {
        return isLoggedIn() && currentUser.getAccountType() == 2;
    }

    /**
     *
     * @return ClientModel , un objet client invité
     */
    public static ClientModel createGuestUser() {
        return new ClientModel(
                0,                  // ID fictif ou réservé en BDD pour "invité"
                0,                  // accountType = 0 → invité
                "Invité",          // prénom
                "",                // nom
                0,                 // âge
                null,              // date de naissance
                "guest@legendaria.com",
                ""                 // mot de passe vide
        );
    }

    /**
     * Permet de se déconnecter
     */
    public static void logout() {
        currentUser = null;
    }
}
