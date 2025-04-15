package toolbox;//Import des packages nécessaires
import Model.ClientModel;

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
     * Permet de se déconnecter
     */
    public static void logout() {
        currentUser = null;
    }
}
