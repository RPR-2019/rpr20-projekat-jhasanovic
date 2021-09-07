package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Language;

public class ControllerHelpUpdate {
    @FXML
    public Label label1;
    @FXML
    public ImageView image1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public ImageView image2;
    @FXML
    public Label label4;
    @FXML
    public ImageView image3;
    private Language l;

    @FXML
    public void initialize() {

        l = Language.getInstance();
        if(l.getLang().equals("bs")){
            label1.setText("Za ažuriranje proizvoda prvo odaberite željeni proizvod iz tabele proizvoda, a zatim kliknite na dugme \"Ažuriraj proizvod\".");
            label2.setText(" Klik na dugme \"Ažuriraj proizvod\" otvara formu za ažuriranje podataka o odabranom proizvodu. ");
            label3.setText("Kada ste završili sa izmjenom podataka o proizvodu, kliknite na dugme \"Ažuriraj proizvod\". ");
            label4.setText("Ažurirani proizvod tada biste trebali vidjeti u listi proizvoda u početnom prozoru.");
            image1.setImage(new Image(getClass().getResourceAsStream("/png/help4_bs.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help5_bs.PNG")));
            image3.setImage(new Image(getClass().getResourceAsStream("/png/help6_bs.PNG")));
        }

        else if(l.getLang().equals("en")){
            label1.setText("To update a product, select a product from the list and then click the button \"Update product\".");
            label2.setText("Clicking the button \"Update product\" opens a new form.");
            label3.setText("Once you finish the data input, click the button \"Update product\".");
            label4.setText("You should now be able to see your updated product in the product list.");
            image1.setImage(new Image(getClass().getResourceAsStream("/png/help4_en.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help5_en.PNG")));
            image3.setImage(new Image(getClass().getResourceAsStream("/png/help6_en.PNG")));
        }
    }
}
