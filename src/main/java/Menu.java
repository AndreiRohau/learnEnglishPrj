import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class Menu {
    public Menu() {

    }

    public void regOrLog(Connection conn) throws SQLException {
        Util.outWrite("THIS IS A PROGRAMME FOR STUDYING ENGLISH \n NOW! \n Choose Registration (r) or Login (l)");
        String RegLogResult = Util.scannr();
        switch (RegLogResult) {
            case "r":
                reg(conn);
                log(conn);
                break;
            case "l":
                log(conn);
                break;
            default:
                regOrLog(conn);
                break;
        }
    }

    public void reg(Connection conn) throws SQLException {
        Util.outWrite("Enter your name to create a profile");
        String name = Util.scannr();
        Util.outWrite("Enter your password to create a profile");
        String password = Util.scannr();
        Util.outWrite("Enter your preferable amount of phrases to be shown per day");
        String phrasesPerDay = Util.scannr();
        DBMethods.userReg(conn, name, password, phrasesPerDay);
    }

    public void log(Connection conn) throws SQLException {
        Util.outWrite("Enter your name");
        String name = Util.scannr();
        Util.outWrite("Enter your password");
        String password = Util.scannr();
        if (DBMethods.veryfyUser(conn, name, password).getId() > 0) {
            System.out.println("your id is " + DBMethods.veryfyUser(conn, name, password).getId());
            menu();
        }else {
            regOrLog(conn);
        }
    }

    public void menu(){
        System.out.println("authorization completed, this is menu");
    }
}
