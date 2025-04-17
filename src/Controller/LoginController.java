package Controller;

import DAO.LoginDAO;
import Model.ClientModel;

import java.util.Map;

/**
 * Contrôleur de connexion.
 */
public class LoginController {

    private final LoginDAO dao;

    public LoginController() {
        dao = new LoginDAO();
    }

    /**
     * Authentifie un utilisateur à partir d'une Map d'infos (email, password).
     * @param credentials map des infos
     * @return un ClientModel correspondant ou invité
     */
    public ClientModel attemptLogin(Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        return dao.authenticate(email, password);
    }
}
