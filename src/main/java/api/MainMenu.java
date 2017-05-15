package api;

import dao.DBMethods;
import module.Module;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static api.Util.outWrite;
import static api.Util.scannr;

/**
 * Created by rohau.andrei on 14.05.2017.
 */
public class MainMenu {

    public void letsStudy(Connection conn, Module user) throws SQLException {
        outWrite("Hello MR: " + user.getName());

        mainMenu(conn, user);
    }

    public void mainMenu(Connection conn, Module user) throws SQLException {
        outWrite("Enter a proper number to choose your next step! (Ex.: 1, 2, 3, 4)" +
                "\n\t1. Add a new phrase into your vocabulary." +
                "\n\t2. Start test!" +
                "\n\t3. Achievements!" +
                "\n\t4. Quit.");
        String answer = scannr();
        switch (answer) {
            case "1":
                //добавляем новую фразу в [phrases], если таковой там еще не было
                //добавляем фразу в список к изучению залогиневшегося
                addPhrase(conn, user.getUserId());
                mainMenu(conn, user);
                break;
            case "2":
                //метод проверки можно ли давать тест! тест только 1 раз за сутки!
                //узнать когда был предыдущий тест ТУТ!!!

                //создаем лист фраз для теста
                HashMap<String, String> list = getTestPhraseList(conn, user);
                outWrite("getTestPhraseList - worked");
                //берем лист этот и загоняем в метод ТЕСТ, где тетируем их
                startTest(list);
                outWrite("StartTest finished! Your results are: ");
                mainMenu(conn, user);
                break;
            case "3":
                //сводная инфа по юзеру - фраз в словаре, тестов он прошел, % правильных ответов
                achieveStatus();
                mainMenu(conn, user);
                break;
            case "4":
                quit();
                break;
        }
    }

    public void addPhrase(Connection conn, int users_id) throws SQLException{
        Module ph = null;
        int ph_id = 0;
        outWrite("First, write below the phrase in RUSSIAN: ");
        String ph_ru = scannr();
        outWrite("Now, write below equivalent phrase in ENGLISH: ");
        String ph_en = scannr();
        outWrite("Confirm if you want to add new phrase: (y) or (n).");
        String confirm = Util.scannr();
        if(confirm.equals("y")) {
            try{
                //проверка залита ли уже фраза? B [phrases]
                ph = DBMethods.selectPhByRuEn(conn, ph_ru, ph_en);
                ph_id = ph.getPhrase_id();
                outWrite("Insert of a new phrase complete RU:\t" + ph_ru + "\tEN:\t" + ph_en + "\tID:\t" + ph_id +
                        "\n\tPhrase was already in the ALL PHRASES TABLE [phrases]");
                //проверка есть ли у юзера уже фраза в [users_phrasess]
                try{
                    DBMethods.selectUserIdPhId(conn, users_id, ph_id);
                    outWrite("This phrase was already in your TO STUDY TABLE [user_phrasess]");
                }catch(NullPointerException ex) {
                    //если нет, добавление новой фразы к списку изучения юзера в [users_phrasess]
                    DBMethods.insertToUserPhrases(conn, users_id, ph_id);
                    outWrite("Insert to your list complete!" +
                            "\n\tusers_id " + String.valueOf(users_id) + "   ph_id " + String.valueOf(ph_id));
                }
            }catch (NullPointerException ex) {
                //походу фраза не залита - ЗАЛИВАЕМ!
                DBMethods.insertNewPhrase(conn, ph_ru, ph_en);
                //вытягиваем айдишку залитой фразы
                ph = DBMethods.selectPhByRuEn(conn, ph_ru, ph_en);
                ph_id = ph.getPhrase_id();
                outWrite("Insert of a new phrase complete RU:\t" + ph_ru + "\tEN:\t" + ph_en + "\tID:\t" + ph_id);
                //добавление новой фразы к списку изучения юзера инсерт айди юзера - айди фразы в [users_phrasess]
                DBMethods.insertToUserPhrases(conn, users_id, ph_id);
                outWrite("Insert to your list complete!" +
                        "\n\tusers_id " + String.valueOf(users_id) + "   ph_id " + String.valueOf(ph_id));
            }
        }else {
            outWrite("Adding new phrase cancelled");
        }
    }

    //метод создания списка из фраз, из [user_phrasess] нашего юзера
    public HashMap<String, String> getTestPhraseList(Connection conn, Module user) throws SQLException{
        outWrite("Creating list of phrases for your test! In process...");
        int phPerDay = Integer.parseInt(user.getPhrasesPerDay());
        int[] phIdList = DBMethods.selectPhByUserId(conn, user);
        HashMap<String, String> list = new HashMap<>();
        for(int i = 0; i < phIdList.length; i++){
            Module phrase = DBMethods.selectPhById(conn, phIdList[i]);
            outWrite("works");
            user.setTestPhrases(list);
            user.getTestPhrases().put(phrase.getPhrase_ru(), phrase.getPhrase_en());
            outWrite("not works");
        }
        return user.getTestPhrases();
    }


    public void startTest(HashMap<String, String> list){
        outWrite("Ready to start test? (y/n)");

        String answer = scannr();
        if(answer.equals("y")){
            //счетчик запуска теста, чтобы выдавать его раз в сутки
            //++ сбор статы о правильных ответах
            //++ удаляются из [user_phrasess] или еще и добавляюся в [user_phrases_failedd]
            for (String key : list.keySet()) {
                outWrite("Translate into ENG: " + key);
                answer = scannr();
                if(answer.equals(list.get(key))){
                    outWrite("RIGHT! " + key + " == " + answer);
                }else {
                    outWrite("Mistake!!!");
                }
                outWrite("Translate into RU: " + list.get(key));
                answer = scannr();
                if(answer.equals(key)){
                    outWrite("RIGHT! " + list.get(key) + " == " + answer);
                }else {
                    outWrite("Mistake!!!");
                }
            }
        }else {
            outWrite("Test wasn't started!");
        }
    }


    public void achieveStatus(){
        //метод к юзеру зальем статус инфу
        //!! метод пишем тут
    }
    public void quit(){
        outWrite("Goodbye. 再见。Arrivederci.");
    }
}
