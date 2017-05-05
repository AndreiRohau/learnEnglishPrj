import db.DBProcessor;

import java.sql.*;

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
        DBProcessor db = new DBProcessor();
        Connection conn = db.getConnection(URLFIXED, USERNAME, PASSWORD);
//        String query = "select * from belhard_project1.phrases where phrase_id = 1 and phrase_ru = \"ХЛОП\""; //where" helps select by ID
//        Statement statement = conn.createStatement();
//        ResultSet resSet = statement.executeQuery(query);
//
//        while(resSet.next()) {
//            int phrase_id;
//            String phrase_ru;
//            String phrase_en;
//            phrase_id = resSet.getInt("phrase_id");
//            phrase_ru = resSet.getString("phrase_ru");
//            phrase_en = resSet.getString("phrase_en");
//            Phrase phrase = new Phrase(phrase_id, phrase_ru, phrase_en);
//
//            System.out.println(phrase);
//        }

//        DBMethods neo = new DBMethods();
//        Phrase ph = neo.selectById(conn, 3);
//        System.out.println(ph);

        Menu start = new Menu();
        start.regOrLog(conn);


        conn.close();

        System.out.println("мы создали юзера нового!!!");
    }


}
