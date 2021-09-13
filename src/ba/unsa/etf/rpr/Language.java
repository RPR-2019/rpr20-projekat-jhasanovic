package ba.unsa.etf.rpr;

public class Language {
    private static Language instance = null;
    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private Language()
    {
        lang = "en";
    }

    public static Language getInstance()
    {
        if (instance == null)
            instance = new Language();

        return instance;
    }
}
