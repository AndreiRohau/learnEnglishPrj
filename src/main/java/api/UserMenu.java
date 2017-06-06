package api;

import dao.DBMethods;
import module.Module;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static api.Util.*;

class UserMenu {

    void letsStudy(Module user) throws SQLException {
        outWrite("Hello MR: " + user.getName());
        mainMenu(user);
    }

    private void mainMenu(Module user) throws SQLException {
        outWrite("Enter a proper number to choose your next step! (Ex.: 1, 2, 3, 4)" +
                "\n\t1. Add a new phrase into your vocabulary." +
                "\n\t2. Start test!" +
                "\n\t3. Achievements!" +
                "\n\t4. Settings" +
                "\n\t5. Quit.");
        String answer = scannr();
        switch (answer) {
            case "1":
                //добавляем пользователю все фразы которых у него в списках нет (вероятен повтор того что было изучено)
                outWrite("There are phrases in vocabulary, want to add them all? (y/n)");
                String res = scannr();
                if(res.equals("y")){
                    //except those you already have
                    addAllPhrases(user);
                }
                //добавляем новую фразу в [phrases], если таковой там еще не было
                //добавляем фразу в список к изучению залогиневшегося
                outWrite("Still have a new phrase to save in your dictionary? (y/n)");
                res = scannr();
                if(res.equals("y")) {
                    addPhrase(user);
                }
                mainMenu(user);
                break;
            case "2":
                if(!detectTime(user)){
                    outWrite("Test could be passed ones per day! (now every 2 min)");
                    mainMenu(user);
                    break;
                }
                //создаем лист фраз для теста
                user = getTestPhraseList(user);
                //берем лист этот и загоняем в метод ТЕСТ, где тетируем их
                if(user.getTestPhrases().keySet().size() > 0) {
                    user = startTest(user);
                    int rightAns = user.getRight() * 100 / (user.getRight() + user.getWrong());
                    outWrite("Test finished! Your results are: " + rightAns + "% of RIGHT answers!");
                }else {
                    outWrite("Did not get phrases for test, NO PHRASES FOUND.");
                }
                mainMenu(user);
                break;
            case "3":
                //сводная инфа по юзеру - фраз в словаре, тестов он прошел, % правильных ответов
                user = achieveStatus(user);
                mainMenu(user);
                break;
            case "4":
                //изменения кол фраз в день
                user = settings(user);
                mainMenu(user);
                break;
            case "5":
                quit();
                break;
            default :
                mainMenu(user);
                break;
        }
    }

    private void addPhrase(Module user) throws SQLException{
        int users_id = user.getUserId();
        Module ph;
        int ph_id;
        outWrite("First, write below the phrase in RUSSIAN: ");
        String ph_ru = scannr();
        outWrite("Now, write below equivalent phrase in ENGLISH: ");
        String ph_en = scannr();
        outWrite("Confirm if you want to add new phrase: (y) or (n).");
        String confirm = Util.scannr();
        if(confirm.equals("y")) {
            try {
                //проверка залита ли уже фраза? B [phrases]
                ph = DBMethods.selectPhByRuEn(ph_ru, ph_en);
                ph_id = ph.getPhrase_id();
                outWrite("Insert of a new phrase complete RU:\t" + ph_ru + "\tEN:\t" + ph_en + "\tID:\t" + ph_id +
                        "\n\tPhrase was already in the ALL PHRASES TABLE [phrases]");
                //проверка есть ли у юзера уже фраза в [users_phrasess]
                try {
                    try {
                        DBMethods.selectUserIdFailedId(users_id, ph_id);
                        outWrite("This phrase was already in your FAILED TABLE [user_phrasess]");
                    }
                    catch (NullPointerException ex) {
                        DBMethods.selectUserIdPhId(users_id, ph_id);
                        outWrite("This phrase was already in your TO STUDY TABLE [user_phrasess]");
                    }
                }
                catch(NullPointerException ex) {
                    //если нет, добавление новой фразы к списку изучения юзера в [users_phrasess]
                    DBMethods.insertToUserPhrases(users_id, ph_id);
                    outWrite("Insert to your list complete!" +
                            "\n\tusers_id " + String.valueOf(users_id) + "   ph_id " + String.valueOf(ph_id));
                }
            }catch (NullPointerException ex) {
                //походу фраза не залита - ЗАЛИВАЕМ!
                DBMethods.insertNewPhrase(ph_ru, ph_en);
                //вытягиваем айдишку залитой фразы
                ph = DBMethods.selectPhByRuEn(ph_ru, ph_en);
                ph_id = ph.getPhrase_id();
                outWrite("Insert of a new phrase complete RU:\t" + ph_ru + "\tEN:\t" + ph_en + "\tID:\t" + ph_id);
                //добавление новой фразы к списку изучения юзера инсерт айди юзера - айди фразы в [users_phrasess]
                DBMethods.insertToUserPhrases(users_id, ph_id);
                outWrite("Insert to your list complete!" +
                        "\n\tusers_id " + String.valueOf(users_id) + "   ph_id " + String.valueOf(ph_id));
            }
            catch(SQLException e) {
                outWrite("Problems with connection to data base.");
            }
        }else {
            outWrite("Adding new phrase cancelled");
        }
    }

    private void addAllPhrases(Module user) throws SQLException {
        try {
            //get phrase_id from [phrases] = put into a list1
            LinkedList<Integer> commonList = DBMethods.selectAllPh();
            //get phrase_id from [user_phrasess] put into list2
            LinkedList<Integer> studyList = DBMethods.selectAllUserPh(user);
            //get phrase_id from [failed_phrase_id] put into list3
            LinkedList<Integer> failedList = DBMethods.selectAllFailedPh(user);
            //заполняем studyList - failedList'om
            studyList.addAll(failedList);
            //final_Ids list = commonList - minus - studyList
            LinkedList<Integer> finalIds = new LinkedList<>();
            for (Integer aCommonList : commonList) {
                boolean found = false;
                int r = aCommonList;
                for (Integer aStudyList : studyList) {
                    int k = aStudyList;
                    if (r == k) {
                        found = true;
                    }
                }
                if (!found) {
                    finalIds.add(r);
                }
            }
            //put PHRASES using final_Ids_list into [user_phrasess]
            for(int x : finalIds) {
                DBMethods.insertToUserPhrases(user.getUserId(), x);
            }
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
        }
    }

    //метод создания списка из фраз, из [user_phrases_failedd] затем из [user_phrasess] нашего юзера
    private Module getTestPhraseList(Module user) throws SQLException{
        outWrite("Creating list of phrases for your test! In process...");
        HashMap<String, String> list = new HashMap<>();
        user.setTestPhrases(list);
        try {
            //from [user_phrases_failedd]
            user = DBMethods.selectPhFailedByUserId(user);
            //из [user_phrasess]
            user = DBMethods.selectPhByUserId(user);
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
        }
        for(int i : user.getPhIdList()){
            try {
                Module phrase = DBMethods.selectPhById(i);
                user.getTestPhrases().put(phrase.getPhrase_ru(), phrase.getPhrase_en());
            }
            catch(NullPointerException ex) {
                outWrite("During creating phrase list found " + ex);
            }
            catch(SQLException e) {
                outWrite("Problems with connection to data base.");
            }
        }
        return user;
    }

    //get try_time event
    private boolean detectTime(Module user) throws SQLException {
        Date date = new Date();
        try {
            long lastEvent = Integer.parseInt(DBMethods.selectLastTestEvent(user).getLastTest());
        long testEvent = date.getTime()/1000; //sec
            return (testEvent >= lastEvent);
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
            return false;
        }

    }

    //метод реализации ТЕСТА
    private Module startTest(Module user) throws SQLException{
        int right = 0;
        int wrong = 0;
        HashMap<String, String> list = user.getTestPhrases();
        outWrite("Ready to start test? (y/n)");
        String answer = scannr();
        if(answer.equals("y")){
            //update tests_tried!
            try {
                user.setTestsTotal(DBMethods.selectTestsTried(user).getTestsTotal() + 1);
                DBMethods.updateTestsTried(user);
            }
            catch(SQLException e) {
                outWrite("Problems with connection to data base.");
            }
            //update [users_last_time] = testEvent + 86400 (sec) = daily
            String TimeTestEvent = String.valueOf(new Date().getTime()/1000 + 120); //86400-day, 3600-hours, 60-min
            try {
                user = DBMethods.updateTimeTestEvent(user, TimeTestEvent);
            }
            catch(SQLException e) {
                outWrite("Problems with connection to data base.");
            }
            Module phrase;
            boolean ru;
            boolean en;
            for (String key : list.keySet()) {
                try {
                    phrase = DBMethods.selectPhByRuEn(key, list.get(key));
                    int phrase_id = phrase.getPhrase_id();
                    //delete phrase from [user_phrasess]
                    DBMethods.deletePh(user, phrase_id);
                    //delete phrase from [user_phrases_failedd]
                    DBMethods.deleteFailedPh(user, phrase_id);
                    //test starts here
                    outWrite("Translate into ENG: " + phrase.getPhrase_ru());
                    answer = scannr();
                    ru = answer.equals(phrase.getPhrase_en());
                    outWrite("Translate into RU: " + phrase.getPhrase_en());
                    answer = scannr();
                    en = answer.equals(phrase.getPhrase_ru());
                    //test result
                    if(ru && en) {
                        right++;
                        outWrite("RIGHT! " + phrase.getPhrase_ru() + " == " + phrase.getPhrase_en());
                    }else {
                        wrong++;
                        outWrite("MISTAKE FOUND! REMEMBER: " + phrase.getPhrase_ru() + " == " + phrase.getPhrase_en());
                        DBMethods.insertToPhrasesFailed(user.getUserId(), phrase_id);
                    }
                }
                catch(SQLException e) {
                    outWrite("Problems with connection to data base.");
                }
            }
        }else {
            outWrite("TEST CANCELED!");
        }
        try {
            //вытягиваем с базы
            user = DBMethods.selectRW(user);
            //обновляем
            user.setRight(user.getRight() + right);
            user.setWrong(user.getWrong() + wrong);
            //закидываем в базу
            user = DBMethods.updateRW(user);
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
        }
        user.setRight(right);
        user.setWrong(wrong);
        return user;
    }

    private int percentage(Module user) throws SQLException {
        try {
            //get DB info
            user = DBMethods.selectRW(user);
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
        }

        int total;
        int r = user.getRight();
        int w = user.getWrong();
        //подсчитываем процент правильных и неправильных ответов
        if(r == 0 && w == 0) {
            outWrite("Try at least one test to get percentage of your \"right/wrong\" answers.");
        }else {
            total = r + w; //total answers
            r = w * 100 / total; //% of wrong answers
            w = r; //% of wrong answers
            r = 100 - w; //% of right answers
        }
        return r;
    }

    //инфо юзера
    private Module achieveStatus(Module user) throws SQLException {
        try {
        //сводная инфа по юзеру - фраз в словаре
        LinkedList<Integer> studyList = DBMethods.selectAllUserPh(user);
        LinkedList<Integer> failedList = DBMethods.selectAllFailedPh(user);
        LinkedList<Integer> totalList = new LinkedList<>();
        totalList.addAll(studyList);
        totalList.addAll(failedList);
        outWrite("Phrases --- \n\tTotal: " + totalList.size() + "" +
                "\n\tIn study list: " + studyList.size() + "" +
                "\n\tIn failed list: " + failedList.size());
        // тестов он прошел,
        int testsTotal = DBMethods.selectTestsTried(user).getTestsTotal();
        outWrite("Total tests tried: " + testsTotal + " time(s)");
        // % правильных ответов
        int rightAnswers = percentage(user);
        outWrite("Percentage of your right answers: " + rightAnswers);
        pressToContinue();
        return user;
        }
        catch(SQLException e) {
            outWrite("Problems with connection to data base.");
            return user;
        }
    }

    //изменение количество слов для теста
    private Module settings(Module user) throws SQLException {
        outWrite("If you want to update amount of phrases for daily tests enter preferable number " +
                "(ex.: 1, 2, 3..), otherwise enter (n)");
        String answer = scannr();
        if(Integer.parseInt(answer) >= 0) {
            try {
                user = DBMethods.updatePhPerDay(user, Integer.parseInt(answer));
                outWrite("Amount of phrases per day updated, now: " + user.getPhrasesPerDay());
            }
            catch(SQLException e) {
                outWrite("Problems with connection to data base.");
            }
        }
        return user;
    }

    //выход из проги
    private void quit(){
        outWrite("Goodbye. 再见。Arrivederci.");
    }
}
