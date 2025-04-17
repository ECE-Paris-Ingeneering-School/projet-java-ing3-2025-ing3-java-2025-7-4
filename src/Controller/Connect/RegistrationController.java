package Controller.Connect;

import DAO.Connect.RegistrationDAO;

import java.util.Map;

public class RegistrationController {

    private final RegistrationDAO dao;

    public RegistrationController() {
        dao = new RegistrationDAO();
    }

    public RegistrationResult handleRegistration(Map<String, String> clientData) {
        try {
            boolean created = dao.insertClient(clientData);
            return created ? RegistrationResult.SUCCESS : RegistrationResult.EMAIL_EXISTS;
        } catch (Exception e) {
            return RegistrationResult.ERROR;
        }
    }

    public enum RegistrationResult {
        SUCCESS,
        EMAIL_EXISTS,
        ERROR
    }
}
