package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Language;

public class ControllerHelpReport {
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public ImageView image3;

    private Language l;


    @FXML
    public void initialize() {
        l = Language.getInstance();
        if(l.getLang().equals("bs")){
            label1.setText("Za kreiranje izvještaja odaberite opciju \"Kreiraj izvještaj\" iz menija File.");
            label2.setText("Odaberite jedan od dva dostupna tipa izvještaja.");
            label3.setText("Kliknite dugme \"Prikaži izvještaj\" za prikaz izvještaja.");
            image1.setImage(new Image(getClass().getResourceAsStream("/png/help13_bs.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help14_bs.PNG")));
            image3.setImage(new Image(getClass().getResourceAsStream("/png/help15_bs.PNG")));

        }

        else if(l.getLang().equals("en")){
            label1.setText("To create a report, select \"Create report\" from the File menu.");
            label2.setText("Pick one of the two available report types.");
            label3.setText("Click the button \"Show report\" to show your report.");

            image1.setImage(new Image(getClass().getResourceAsStream("/png/help13_en.png")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help14_en.PNG")));
            image3.setImage(new Image(getClass().getResourceAsStream("/png/help15_en.PNG")));

        }
    }
}
