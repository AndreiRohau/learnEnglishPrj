package module;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String phrasesPerDay;

    public User(){}
    public User(int id, String name, String password, String phrasesPerDay) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phrasesPerDay = phrasesPerDay;
    }

    @Override
    public String toString(){
        return "module.User [ " + name + "\t" + password + "\t" +phrasesPerDay + " ]";
    }

    public int getId() {
        return this.id;
    }

}
