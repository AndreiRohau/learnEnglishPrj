package dao;

import api.Util;
import module.Module;
import java.sql.*;
import java.util.LinkedList;

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

    //get last_test dayTime
    public static Module selectLastTestEvent(Connection conn, Module user) throws SQLException {
        String query = "select * from belhard_project1.users " +
                "where user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        String users_last_time = null;
        while(resSet.next()) {
            users_last_time = resSet.getString("users_last_time");
        }
        statement.close();
        user.setLastTest(users_last_time);
        return user;
    }

    //get total tests tried
    public static Module selectTestsTried(Connection conn, Module user) throws SQLException {
        String query = "select * from belhard_project1.users " +
                "where user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        int user_test_tried = 0;
        while(resSet.next()) {
            user_test_tried = resSet.getInt("user_test_tried");
        }
        statement.close();
        user.setTestsTotal(user_test_tried);
        return user;
    }

    //get right wrong answers
    public static Module selectRW(Connection conn, Module user) throws SQLException {
        String query = "select * from belhard_project1.users " +
                "where user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        int right = 0;
        int wrong = 0;
        while(resSet.next()) {
            right = resSet.getInt("rightAns");
            wrong = resSet.getInt("wrongAns");
        }
        statement.close();
        user.setRight(right);
        user.setWrong(wrong);
        return user;
    }

    //update TEST HAPPEND time
    public static Module updateTimeTestEvent(Connection conn, Module user, String TimeTestEvent) throws SQLException {
        String query = "update belhard_project1.users set users_last_time = \"" + TimeTestEvent +
                "\" WHERE user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(query);
        user.setLastTest(TimeTestEvent);
        return user;
    }

    //update amount of tests tried
    public static Module updateTestsTried(Connection conn, Module user) throws SQLException {
        int testsTotal = user.getTestsTotal();
        String query = "update belhard_project1.users set user_test_tried = \"" + testsTotal +
                "\" WHERE user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(query);
        user.setTestsTotal(testsTotal);
        return user;
    }

    //update right wrong answers
    public static Module updateRW(Connection conn, Module user) throws SQLException {
        String query = "update belhard_project1.users set rightAns = \"" + user.getRight() +
                "\" WHERE user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
        query = "update belhard_project1.users set wrongAns = \"" + user.getWrong() +
                "\" WHERE user_id = " + user.getUserId();
        statement = conn.createStatement();
        statement.executeUpdate(query);
        return user;
    }


    // поиск фразы по айди в таблице [phrases]
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

    // пo [phrases_id] фразы у юзера по [users_id] в таблице [user_phrases_failedd]
    public static Module selectUserIdFailedId(Connection conn, int users_id, int phrases_id) throws SQLException {
        String query = "select * from belhard_project1.user_phrases_failedd " +
                "where users_id = \"" + users_id + "\" and failed_phrase_id = \"" + phrases_id +"\"";
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Module userPhrase = null;
        while(resSet.next()) {
            int user_id = resSet.getInt("users_id");
            int phrase_id = resSet.getInt("failed_phrase_id");
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

    //вытягиваем ID всеx фраз из таблицы [phrases]
    public static LinkedList<Integer> selectAllPh(Connection conn) throws SQLException {
        LinkedList<Integer> allPhList = new LinkedList<>();
        String query = "select * from belhard_project1.phrases";
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        Module phrase = null;
        while(resSet.next()) {
            int phrase_id = resSet.getInt("phrase_id");
            allPhList.add(phrase_id);
        }
        statement.close();
        return allPhList;
    }

    //вытягиваем ID всеx фраз [user_id] из таблицы [user_phrasess]
    public static LinkedList<Integer> selectAllUserPh(Connection conn, Module user) throws SQLException {
        LinkedList<Integer> allPhList = new LinkedList<>();
        String query = "select * from belhard_project1.user_phrasess where users_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        while(resSet.next()) {
            int phrases_id = resSet.getInt("phrases_id");
            allPhList.add(phrases_id);
        }
        statement.close();
        return allPhList;
    }

    //вытягиваем ID всеx фраз [user_id] из таблицы [user_phrases_failedd]
    public static LinkedList<Integer> selectAllFailedPh(Connection conn, Module user) throws SQLException {
        LinkedList<Integer> allFailedPhList = new LinkedList<>();
        String query = "select * from belhard_project1.user_phrases_failedd where users_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        while(resSet.next()) {
            int failed_phrase_id = resSet.getInt("failed_phrase_id");
            allFailedPhList.add(failed_phrase_id);
        }
        statement.close();
        return allFailedPhList;
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

    // список [phrases_id] по [user_id] в таблице [user_phrases_failedd]
    public static Module selectPhFailedByUserId(Connection conn, Module user) throws SQLException {
        //достаем кол-во фраз для теста пользователя
        int phPerDay = Integer.parseInt(user.getPhrasesPerDay());
        //массив по размеру количества фраз, для заполнения его [phrases_id]
        int[] phIdListFailed = new int[phPerDay];
        String query = "select * from belhard_project1.user_phrases_failedd where users_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        //заполняем пока существуют [phrases_id] , или пока хватает места в массиве - [phrases_id]
        while(resSet.next()) {
            int phrases_id = resSet.getInt("failed_phrase_id");
            if(phPerDay > 0 ) {
                phPerDay--;
                phIdListFailed[phPerDay] = phrases_id;
            }
        }
        statement.close();
        LinkedList<Integer> phIdList = new LinkedList();
        //создаем лист и переносим объекты из массива в лист
        for(int e : phIdListFailed) {
            if (e != 0) {
                phIdList.add(e);
            }
        }
        //закидываем лист к экземпляру ЮЗЕРА
        user.setPhIdList(phIdList);
        return user;
    }

    // список [phrases_id] по [user_id] в таблице [user_phrasess]
    public static Module selectPhByUserId(Connection conn, Module user) throws SQLException {
        int phPerDay = Integer.parseInt(user.getPhrasesPerDay());
        //количество недостающий храз для теста
        LinkedList<Integer> list = user.getPhIdList();
        int amount = phPerDay - list.size();
        int[] phIdArr = new int[amount];
        String query = "select * from belhard_project1.user_phrasess where users_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        ResultSet resSet = statement.executeQuery(query);
        while(resSet.next()) {
            int phrases_id = resSet.getInt("phrases_id");
            if(amount > 0) {
                amount--;
                phIdArr[amount] = phrases_id;
            }
        }
        statement.close();
        //переносим объекты из массива в лист экземпляра юзера
        for(int e : phIdArr) {
            if (e != 0) {
                list.add(e);
            }else {
                Util.outWrite("didnt work");
            }
        }
        //закидываем лист к экземпляру ЮЗЕРА
        user.setPhIdList(list);
        return user;
    }

    //update users ph per Day
    public static Module updatePhPerDay(Connection conn, Module user, int phPerDay) throws SQLException {
        String query = "update belhard_project1.users set user_phrases_per_day = " + phPerDay +
                " WHERE user_id = " + user.getUserId();
        Statement statement = conn.createStatement();
        int result = statement.executeUpdate(query);
        user.setPhrasesPerDay(String.valueOf(phPerDay));
        return user;
    }

    //delete phrase from [user_phrasess]
    public static void deletePh(Connection conn, Module user, int ph) throws SQLException {
        String query = "delete from belhard_project1.user_phrasess where users_id = " + user.getUserId() +
                " and phrases_id = " + ph;
        Statement statement = conn.createStatement();
        boolean result = statement.execute(query);
    }

    //delete phrase from [user_phrases_failedd]
    public static void deleteFailedPh(Connection conn, Module user, int ph) throws SQLException {
        String query = "delete from belhard_project1.user_phrases_failedd where users_id = " + user.getUserId() +
                " and failed_phrase_id = " + ph;
        Statement statement = conn.createStatement();
        boolean result = statement.execute(query);
    }

    //добавление фразы пользователю в таблицу [user_phrases_failedd]
    public static void insertToPhrasesFailed(Connection conn, int user_id, int phrase_id) throws SQLException {
        String INSERT = "insert into belhard_project1.user_phrases_failedd " +
                "(users_id, failed_phrase_id) values (?,?)";
        PreparedStatement prepInsert = conn.prepareStatement(INSERT);
        prepInsert.setInt(1,user_id);
        prepInsert.setInt(2,phrase_id);
        prepInsert.execute();
        prepInsert.close();
    }


}
