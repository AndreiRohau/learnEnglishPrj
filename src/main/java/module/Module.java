package module;


import java.util.HashMap;

/**
 * Created by rohau.andrei on 12.05.2017.
 */
public class Module {

    private int userId;
    private String name;
    private String password;
    private String phrasesPerDay;
    private HashMap<String, String> allPhrases;
    private HashMap<String, String> testPhrases;
    private int phrase_id;
    private String phrase_ru;
    private String phrase_en;

    public Module() {
    }

    public Module(int phrase_id, String phrase_ru, String phrase_en) {
        this.phrase_id = phrase_id;
        this.phrase_ru = phrase_ru;
        this.phrase_en = phrase_en;
    }


    public Module(int id, String name, String password, String phrasesPerDay) {
        this.userId = id;
        this.name = name;
        this.password = password;
        this.phrasesPerDay = phrasesPerDay;
    }

//    @Override
//    public String toString(){
//        return "module.Phrase [ " + phrase_id + "\t" + phrase_ru + "\t" +phrase_en + " ]";
//    }

    @Override
    public String toString(){
        return "module.User [ " + name + "\t" + password + "\t" +phrasesPerDay + " ]";
    }

    //getters & setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhrasesPerDay() {
        return phrasesPerDay;
    }

    public void setPhrasesPerDay(String phrasesPerDay) {
        this.phrasesPerDay = phrasesPerDay;
    }

    public HashMap<String, String> getAllPhrases() {
        return allPhrases;
    }

    public void setAllPhrases(HashMap<String, String> allPhrases) {
        this.allPhrases = allPhrases;
    }

    public HashMap<String, String> getTestPhrases() {
        return testPhrases;
    }

    public void setTestPhrases(HashMap<String, String> testPhrases) {
        this.testPhrases = testPhrases;
    }

    public int getPhrase_id() {
        return phrase_id;
    }

    public void setPhrase_id(int phrase_id) {
        this.phrase_id = phrase_id;
    }

    public String getPhrase_ru() {
        return phrase_ru;
    }

    public void setPhrase_ru(String phrase_ru) {
        this.phrase_ru = phrase_ru;
    }

    public String getPhrase_en() {
        return phrase_en;
    }

    public void setPhrase_en(String phrase_en) {
        this.phrase_en = phrase_en;
    }
}
