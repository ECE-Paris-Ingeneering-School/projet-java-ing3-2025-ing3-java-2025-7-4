// ======================= REGISTRATION CONTROLLER =======================
package registration;

import java.util.Map;

/**
 * Contrôleur gérant la logique de création de compte.
 * Il interagit avec le DAO pour vérifier l'unicité de l'email et insérer en BDD.
 */
public class RegistrationController {

    private final RegistrationDAO dao;

    public RegistrationController() {
        dao = new RegistrationDAO();
    }

    /**
     * Tente de créer un nouveau compte client.
     * @param clientData Map avec les infos du client : name, email, password, age
     * @return true si le compte a été créé, false si l'email existe déjà
     */
    public boolean registerNewClient(Map<String, String> clientData) {
        return dao.insertClient(clientData);
    }
} 