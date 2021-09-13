package ba.unsa.etf.rpr;

public class LanguageChanged {
    private static LanguageChanged instance = null;
    private boolean flag;

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private LanguageChanged() {
        flag = false;
    }

    public static LanguageChanged getInstance() {
        if (instance == null)
            instance = new LanguageChanged();

        return instance;
    }
}
