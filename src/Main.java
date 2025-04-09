import DAO.DaoFactory;

public class Main {
    public static void main(String[] args) {

        //ligne de code à changer en fonction des paramètres
        DaoFactory dao = DaoFactory.getInstance("parcAttractions", "root", "");
        System.out.println(dao);
    }
}
