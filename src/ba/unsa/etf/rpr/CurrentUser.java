package ba.unsa.etf.rpr;

public class CurrentUser {
    private static CurrentUser instance;
    private String username;

    private CurrentUser(){
        username="root";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static CurrentUser getInstance()
    {
        if (instance == null)
            instance = new CurrentUser();

        return instance;
    }
}
