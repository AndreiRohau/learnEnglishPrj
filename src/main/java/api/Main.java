package api;

import db.DBProcessor;
import module.Module;
import java.sql.*;
import static api.Util.outWrite;
/**
 * Created by rohau.andrei on 04.05.2017.
 */
public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private final static String URLFIXED = URL + "?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException {
        //getting connection to database
        DBProcessor db = new DBProcessor();
        Connection conn = db.getConnection(URLFIXED, USERNAME, PASSWORD);

        //STARTmenu!!!!!
        Module user = LoginMenu.regOrLog(conn);
        //END startMenu!!!!!

        //UserMenu!!!!!
        try {
            UserMenu nonStaticMainMenu = new UserMenu();
            nonStaticMainMenu.letsStudy(conn, user);
        }
        catch (NullPointerException ex){
            outWrite("PROGRAMME WAS CLOSED BY YOU");
        }
        //END UserMenu!!!!!

        conn.close();
    }
}
