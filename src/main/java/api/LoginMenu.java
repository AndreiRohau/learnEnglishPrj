package api;

import dao.DBMethods;
import module.Module;

import java.sql.SQLException;

import static api.Util.outWrite;
import static api.Util.scannr;

class LoginMenu {

    static Module regOrLog() throws SQLException {
        Module user = null;
        outWrite("WELCOME TO A PROGRAMME FOR STUDYING ENGLISH " +
                "\n\tNOW! " +
                "\n\tChoose Registration (r) or Login (l).\tQuit (q)");
        String answer = scannr();
        switch (answer) {
            case "q":
                break;
            default:
                outWrite("Enter:\t\"r\" to register or\t \"l\" to log in or\t \"q\" to quit");
                user = regOrLog();
                break;
            case "r":
                reg();
                user = regOrLog();
                break;
            case "l":
                user = log();
                break;
        }
        return user;
    }

    private static void reg() throws SQLException {
        outWrite("Enter your name to create a profile");
        String name = scannr();
        outWrite("Enter your password to create a profile");
        String password = scannr();
        outWrite("Enter your preferable amount of phrases to be shown per day");
        String phrasesPerDay = scannr();
        outWrite("Confirm if you want to create new profile: (y) or (n).");
        String confirm = Util.scannr();
        if(confirm.equals("y")) {
            try {
                DBMethods.insertUserReg(name, password, phrasesPerDay);
                outWrite("Registration complete NAME:\t" + name + "\tpassword:\t" + password);
            }
            catch(SQLException e) {
                outWrite("Problems with connection to data base.");
            }
        }else {
            outWrite("Registration cancelled");
        }
    }

    private static Module log() throws SQLException {
        outWrite("Enter your name below to log in: ");
        String name = scannr();
        outWrite("Enter your password to log in: ");
        String password = scannr();
        Module user = null;
        try{
            user = DBMethods.selectVerifyUser(name, password);
            outWrite("Authorization completed, LET's STUDY!" +
                    "\n\tFYI your profile's ID is:\t" + DBMethods.selectVerifyUser(name, password).getUserId());
        }catch(NullPointerException ex) {
            outWrite("Login failed!!! Try again!");
            regOrLog();
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
        }

        return user;
    }

}
