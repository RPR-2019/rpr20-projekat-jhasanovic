package sample;

public class Language {
    private static Language single_instance = null;

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
        if (single_instance == null)
            single_instance = new Language();

        return single_instance;
    }
}
