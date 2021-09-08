package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Language;

public class ControllerHelpRemove {
    @FXML
    public Label label1;
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;

    @FXML
    public void initialize() {
        Language l = Language.getInstance();

        if(l.getLang().equals("bs")){
            label1.setText("Za brisanje proizvoda prvo odaberite željeni proizvod iz tabele proizvoda, a zatim kliknite na dugme \"Ukloni proizvod\".");

            image1.setImage(new Image(getClass().getResourceAsStream("/png/help7_bs.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help8_bs.PNG")));
        }

        else if(l.getLang().equals("en")){
            label1.setText("To delete a product, select a product from the list and then click the button \"Remove product\".");

            image1.setImage(new Image(getClass().getResourceAsStream("/png/help7_en.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help8_en.PNG")));
        }
    }
}
