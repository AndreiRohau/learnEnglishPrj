import java.sql.*;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class DBMethods {
    //DBMethods(){}

    //insert
    public static void userReg(Connection conn, String name, String password, String phrasesPerDay) throws SQLException {
        String INSERT = "insert into belhard_project1.users " +
                "(user_name, user_password, user_phrases_per_day) values (?,?,?)";
        PreparedStatement prepInsert = conn.prepareStatement(INSERT);
        prepInsert.setString(1,name);
        prepInsert.setString(2,password);
        prepInsert.setString(3,phrasesPerDay);
        prepInsert.execute();
        prepInsert.close();
    }

    //insert you Connection and required phrase's ID
    public static Phrase selectById(Connection conn, int id) throws SQLException {
        String query = "select * from belhard_project1.phrases where phrase_id = " + id;
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Phrase phrase = null;
        while(resSet.next()) {
            int phrase_id = resSet.getInt("phrase_id");
            String phrase_ru = resSet.getString("phrase_ru");
            String phrase_en = resSet.getString("phrase_en");
            phrase = new Phrase(phrase_id, phrase_ru, phrase_en);
        }
        statement.close();
        return phrase;
    }

    public static User veryfyUser(Connection conn, String name, String password) throws SQLException {
        int id;
        String query = "select * from belhard_project1.users " +
                "where user_name = \"" + name + "\" and user_password = \"" + password +"\"";
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        User user = null;
        while(resSet.next()) {
            int user_id = resSet.getInt("user_id");
            String user_name = resSet.getString("user_name");
            String user_password = resSet.getString("user_password");
            String user_phrases_per_day = resSet.getString("user_phrases_per_day");
            user = new User(user_id, user_name, user_password, user_phrases_per_day);
        }
        statement.close();

        return user;
    }



}
