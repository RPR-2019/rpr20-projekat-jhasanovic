package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {//osiguravamo da ce biti otvorena samo jedna konekcija na bazu da ne bi doslo do lockanja
    private static Connection conn = null;
    public static Connection getConn() throws SQLException {
        if(conn == null){
            String url = "jdbc:sqlite:apoteka.db";
            conn = DriverManager.getConnection(url);
        }
        return conn;
    }
}
