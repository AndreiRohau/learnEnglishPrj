package module;

/**
 * Created by rohau.andrei on 05.05.2017.
 */
public class Phrase {
    private int phrase_id;
    private String phrase_ru;
    private String phrase_en;

    public Phrase(){}
    public Phrase(int phrase_id, String phrase_ru, String phrase_en) {
        this.phrase_id = phrase_id;
        this.phrase_ru = phrase_ru;
        this.phrase_en = phrase_en;
    }

    @Override
    public String toString(){
        return "module.Phrase [ " + phrase_id + "\t" + phrase_ru + "\t" +phrase_en + " ]";
    }


}
