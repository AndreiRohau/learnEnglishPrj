package api;

import db.DBProcessor;
import module.Module;
import java.sql.*;
import static api.Util.outWrite;

/**
 * Created by rohau.andrei on 04.05.2017.
 * скрипты в СКЛ файл!!!!! по созданию таблиц в БД!!
 * статистику -
 * сколько у него фраз в словаре, сколько тестов он прошел, % правильных ответов.
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

        //START TESTS----------------------------------------------

        //test get phrase by ID
//        String query = "select * from belhard_project1.phrases where phrase_id = 1 and phrase_ru = \"ХЛОП\""; //where" helps select by ID
//        Statement statement = conn.createStatement();
//        ResultSet resSet = statement.executeQuery(query);
//        while(resSet.next()) {
//            int phrase_id;
//            String phrase_ru;
//            String phrase_en;
//            phrase_id = resSet.getInt("phrase_id");
//            phrase_ru = resSet.getString("phrase_ru");
//            phrase_en = resSet.getString("phrase_en");
//            module.Module phrase = new module.Module(phrase_id, phrase_ru, phrase_en);
//
//            System.out.println(phrase);
//        }
//        dao.DBMethods neo = new dao.DBMethods();
//        module.Module ph = neo.selectById(conn, 3);
//        System.out.println(ph);

        //test add new phrase method
//        UserMenu ph = new UserMenu();
//        ph.addPhrase(conn, 13);

        // END TESTS-----------------------------------------------------

        //STARTmenu!!!!!
        Module user = StartMenu.regOrLog(conn);
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
