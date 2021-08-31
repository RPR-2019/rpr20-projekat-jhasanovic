package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale.setDefault(new Locale("bs", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/language.fxml"),bundle);
        Parent root = loader.load();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }


    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        //String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/.mojapp/apoteka.db";
        String url = "jdbc:sqlite:apoteka.db";
        try {
            Connection conn = DriverManager.getConnection(url);
           /* Statement stmt = conn.createStatement();
           */
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM proizvod WHERE name=?");
            ps.setString(1,"Paracetamol");
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("id"));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Greška u radu sa bazom podataka");
            System.out.println(e.getMessage());
        }
        launch(args);
    }
}
