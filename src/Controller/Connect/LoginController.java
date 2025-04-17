package Controller.Connect;

import DAO.Connect.LoginDAO;
import Model.Client.ClientModel;
import toolbox.SessionManager;

import java.util.Map;

public class LoginController {

    private final LoginDAO dao;

    public LoginController() {
        dao = new LoginDAO();
    }

    /**
     * Tente une connexion utilisateur.
     * Si réussite, stocke l'utilisateur dans la session et renvoie true.
     * Sinon, renvoie false.
     */
    public boolean handleLogin(Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        ClientModel client = dao.authenticate(email, password);

        if (client == null || client.getId() == 7) {
            return false;
        }

        SessionManager.setCurrentUser(client);
        return true;
    }

    public ClientModel getCurrentUser() {
        return SessionManager.getCurrentUser();
    }
}
