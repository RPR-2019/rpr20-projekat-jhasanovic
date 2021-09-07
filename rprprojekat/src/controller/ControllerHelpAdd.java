package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sample.Language;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ControllerHelpAdd {
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public ImageView image3;
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public Label label4;
    @FXML
    public VBox vBoxContainer;


    private Language l;

    @FXML
    public void initialize() {

        l = Language.getInstance();
        if(l.getLang().equals("bs")){
            label1.setText("Za dodavanje novog proizvoda kliknite na dugme \"Dodaj proizvod\".");
            label2.setText("Klik na dugme \"Dodaj proizvod\" otvara formu za unos podataka o novom proizvodu.");
            label3.setText("Kada ste završili sa popunjavanjem podataka o proizvodu, kliknite na dugme \"Dodaj proizvod\".");
            label4.setText("Novi proizvod sada biste trebali vidjeti u listi proizvoda u početnom prozoru.");
            image1.setImage(new Image(getClass().getResourceAsStream("/png/help1_bs.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help2_bs.PNG")));
            image3.setImage(new Image(getClass().getResourceAsStream("/png/help3_bs.PNG")));
        }


        else if(l.getLang().equals("en")){
            label1.setText("To add a new product, click the button \"Add product\".");
            label2.setText("Clicking the button \"Add product\" opens a new form.");
            label3.setText("Once you finish the data input, click the button \"Add product\".");
            label4.setText("You should now be able to see your added product in the product list.");
            image1.setImage(new Image(getClass().getResourceAsStream("/png/help1_en.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help2_en.PNG")));
            image3.setImage(new Image(getClass().getResourceAsStream("/png/help3_en.PNG")));
        }
    }
}
