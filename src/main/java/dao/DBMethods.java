package dao;

import api.Util;
import module.Module;
import java.sql.*;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class DBMethods {
    //registration - insert
    public static void insertUserReg(Connection conn, String name, String password, String phrasesPerDay) throws SQLException{
        String INSERT = "insert into belhard_project1.users " +
                "(user_name, user_password, user_phrases_per_day) values (?,?,?)";
        PreparedStatement prepInsert = conn.prepareStatement(INSERT);
        prepInsert.setString(1,name);
        prepInsert.setString(2,password);
        prepInsert.setString(3,phrasesPerDay);
        prepInsert.execute();
        prepInsert.close();
    }

    //login! verification
    public static Module selectVerifyUser(Connection conn, String name, String password) throws SQLException {
        String query = "select * from belhard_project1.users " +
                "where user_name = \"" + name + "\" and user_password = \"" + password +"\"";
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Module user = null;
        while(resSet.next()) {
            int user_id = resSet.getInt("user_id");
            String user_name = resSet.getString("user_name");
            String user_password = resSet.getString("user_password");
            String user_phrases_per_day = resSet.getString("user_phrases_per_day");
            user = new Module(user_id, user_name, user_password, user_phrases_per_day);
        }
        statement.close();
        return user;
    }

    // поиск фразы по айди в таблице phrases
    public static Module selectPhById(Connection conn, int id) throws SQLException {
        String query = "select * from belhard_project1.phrases where phrase_id = " + id;
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Module phrase = null;
        while(resSet.next()) {
            int phrase_id = resSet.getInt("phrase_id");
            String phrase_ru = resSet.getString("phrase_ru");
            String phrase_en = resSet.getString("phrase_en");
            phrase = new Module(phrase_id, phrase_ru, phrase_en);
        }
        statement.close();
        return phrase;
    }

    // пo [phrases_id] фразы у юзера по [users_id] в таблице [user_phrasess]
    public static Module selectUserIdPhId(Connection conn, int users_id, int phrases_id) throws SQLException {
        String query = "select * from belhard_project1.user_phrasess " +
                "where users_id = \"" + users_id + "\" and phrases_id = \"" + phrases_id +"\"";
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Module userPhrase = null;
        while(resSet.next()) {
            int user_id = resSet.getInt("users_id");
            int phrase_id = resSet.getInt("phrases_id");
            userPhrase = new Module(user_id, phrase_id);
        }
        statement.close();
        return userPhrase;
    }

    // поиск фразы по RU EN в [phrases]
    public static Module selectPhByRuEn(Connection conn, String ph_ru, String  ph_en) throws SQLException {
        String query = "select * from belhard_project1.phrases " +
                "where phrase_ru = \"" + ph_ru + "\" and phrase_en = \"" + ph_en +"\"";
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Module phrase = null;
        while(resSet.next()) {
            int phrase_id = resSet.getInt("phrase_id");
            String phrase_ru = resSet.getString("phrase_ru");
            String phrase_en = resSet.getString("phrase_en");
            phrase = new Module(phrase_id, phrase_ru, phrase_en);
        }
        statement.close();
        return phrase;
    }

    //добавление новой фразы
    public static void insertNewPhrase(Connection conn, String ph_ru, String ph_en) throws SQLException {
        String INSERT = "insert into belhard_project1.phrases " +
                "(phrase_ru, phrase_en) values (?,?)";
        PreparedStatement prepInsert = conn.prepareStatement(INSERT);
        prepInsert.setString(1,ph_ru);
        prepInsert.setString(2,ph_en);
        prepInsert.execute();
        prepInsert.close();
    }

    //добавление фразы пользователю в таблицу для изучения user_phrasess
    public static void insertToUserPhrases(Connection conn, int user_id, int phrase_id) throws SQLException {
        String INSERT = "insert into belhard_project1.user_phrasess " +
                "(users_id, phrases_id) values (?,?)";
        PreparedStatement prepInsert = conn.prepareStatement(INSERT);
        prepInsert.setInt(1,user_id);
        prepInsert.setInt(2,phrase_id);
        prepInsert.execute();
        prepInsert.close();

    }

    // список [phrases_id] по [user_id] в таблице [user_phrasess]
    public static int[] selectPhByUserId(Connection conn, Module user) throws SQLException {
        int phPerDay = Integer.parseInt(user.getPhrasesPerDay());
        int[] phIdList = new int[phPerDay];
        //Util.outWrite("phPerDay " + phPerDay + " phIdList " + phIdList.length);
        String query = "select * from belhard_project1.user_phrasess where users_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        while(resSet.next()) {
            int phrases_id = resSet.getInt("phrases_id");
            if(phPerDay > 0) {
                phPerDay--;
                phIdList[phPerDay] = phrases_id;
                //Util.outWrite("ADDED " + phrases_id);
            }
        }
        statement.close();
        return phIdList;
    }



}
