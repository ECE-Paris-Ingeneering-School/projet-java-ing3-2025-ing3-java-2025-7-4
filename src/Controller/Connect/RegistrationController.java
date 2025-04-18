package Controller.Connect;

import DAO.Connect.RegistrationDAO;

import java.util.Map;

/**
 * La classe `RegistrationController` gère l'enregistrement des utilisateurs.
 * Le constructeur initialise l'objet `dao` en créant une nouvelle instance de `RegistrationDAO`.
 */

public class RegistrationController {

    private final RegistrationDAO dao;

    public RegistrationController() {
        dao = new RegistrationDAO();
    }

    /**
     *
     * @param clientData ensemble de données utilisateur (comme email, mot de passe, etc.) sous forme de paires clé-valeur.
     * @return indique si l'inscription a réussi, échoué ou si une erreur s'est produite.
     */
    public RegistrationResult handleRegistration(Map<String, String> clientData) {
        try {
            boolean created = dao.insertClient(clientData);
            return created ? RegistrationResult.SUCCESS : RegistrationResult.EMAIL_EXISTS;
        } catch (Exception e) {
            return RegistrationResult.ERROR;
        }
    }

    /**
     * L'énumération `RegistrationResult` définit les différents résultats possibles d'une tentative d'inscription : succès, e-mail existant ou erreur
     */

    public enum RegistrationResult {
        SUCCESS,
        EMAIL_EXISTS,
        ERROR
    }
}
