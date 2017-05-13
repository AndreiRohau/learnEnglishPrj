package api;

import dao.DBMethods;
import module.Module;

import java.sql.Connection;
import java.sql.SQLException;
import static api.Util.*;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class StartMenu {

    public static Module regOrLog(Connection conn) throws SQLException {
        outWrite("THIS IS A PROGRAMME FOR STUDYING ENGLISH \n NOW! \n Choose Registration (r) or Login (l)." +
                "\tQuit (q)");
        String RegLogResult = scannr();
        Module user = null;
        switch (RegLogResult) {
            case "q":
                break;
            default:
                outWrite("Enter:\t\"r\" to register or\t \"l\" to log in or\t \"q\" to quit");
                regOrLog(conn);
                break;
            case "r":
                reg(conn);
                user = regOrLog(conn);
                break;
            case "l":
                user = log(conn);
                break;
        }
        return user;
    }

    public static void reg(Connection conn) throws SQLException {
        outWrite("Enter your name to create a profile");
        String name = scannr();
        outWrite("Enter your password to create a profile");
        String password = scannr();
        outWrite("Enter your preferable amount of phrases to be shown per day");
        String phrasesPerDay = scannr();
        outWrite("Confirm if you want to create new profile: (y) or (n).");
        String confirm = Util.scannr();
        if(confirm.equals("y")) {
            DBMethods.userReg(conn, name, password, phrasesPerDay);
            outWrite("Registration complete NAME:\t" + name + "\tpassword:\t" + password);
        }else {
            outWrite("Registration cancelled");
        }
    }

    public static Module log(Connection conn) throws SQLException {
        outWrite("Enter your name below to log in: ");
        String name = scannr();
        outWrite("Enter your password to log in: ");
        String password = scannr();
        Module user = null;
        int user_id = 0;
        try{
            user = DBMethods.verifyUser(conn, name, password);
            user_id = user.getUserId();
            outWrite("Authorization completed, LET's STUDY!\t" +
                    "\tFYI your profile's ID is:\t" + DBMethods.verifyUser(conn, name, password).getUserId());
        }catch(NullPointerException ex) {
            outWrite("Login failed!!! Try again!");
            regOrLog(conn);
        }
        return user;
    }

}
