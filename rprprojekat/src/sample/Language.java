package sample;

public class Language {
    private static Language instance = null;
    public String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private Language()
    {
        lang = "bs";
    }

    public static Language getInstance()
    {
        if (instance == null)
            instance = new Language();

        return instance;
    }
}
