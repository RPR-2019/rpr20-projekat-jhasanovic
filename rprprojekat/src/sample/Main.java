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
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Locale.setDefault(new Locale("bs", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/language.fxml"),bundle);
        Parent root = loader.load();
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();

    }


    public static void main(String[] args) throws SQLException {
       /* ProductDAO dao=ProductDAO.getInstance();
        System.out.println("Unesite id proizvoda: ");
        Scanner ulaz=new Scanner(System.in);
        String sifra=ulaz.nextLine();

        for(Product p:dao.pretraga(sifra))
            System.out.println("ID: "+p.getID()+" naziv: "+p.getName());*/
        launch(args);
    }
}
