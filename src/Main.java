
import DAO.DaoFactory;
import view.AttractionView;

public class Main {
    public static void main(String[] args) {
        // Connexion à la base (adapter le mot de passe si nécessaire)
        DaoFactory daoFactory = DaoFactory.getInstance("attractions_db", "root", "");

        // Simuler un utilisateur connecté (à adapter avec la vraie gestion plus tard)
        boolean isLoggedIn = false;   // ou true
        boolean isAdmin = false;      // selon le type d'utilisateur
        String userName = "Visiteur"; // ou un vrai nom après login

        // Lancer la vue d'accueil
        javax.swing.SwingUtilities.invokeLater(() -> new AttractionView(isLoggedIn, isAdmin, userName));
    }
}
