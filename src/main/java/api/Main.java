package api;

import module.Module;

import java.sql.SQLException;

import static api.Util.outWrite;

public class Main {
    public static void main(String[] args) throws SQLException {
        Module user = LoginMenu.regOrLog();

        try {
            new UserMenu().letsStudy(user);
        }
        catch (NullPointerException ex){
            outWrite("PROGRAMME WAS CLOSED BY YOU");
        }

    }
}
