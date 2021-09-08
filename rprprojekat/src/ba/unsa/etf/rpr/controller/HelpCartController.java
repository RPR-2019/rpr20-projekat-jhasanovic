package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.Language;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelpCartController {
    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public Label label4;
    @FXML
    public ImageView image1;
    @FXML
    public ImageView image2;
    @FXML
    public Label label5;
    @FXML
    public Label label6;
    @FXML
    public Label label7;
    @FXML
    public ImageView image4;
    @FXML
    public ImageView image5;

    @FXML
    public void initialize() {
        Language l = Language.getInstance();
        if(l.getLang().equals("bs")){
            label1.setText("Za dodavanje proizvoda u korpu prvo odaberite željeni proizvod iz tabele proizvoda, a zatim kliknite na dugme \"Dodaj u korpu\".");
            label2.setText("Klik na dugme \"Dodaj u korpu\" otvara novi prozor sa detaljnim informacijama o proizvodu.");
            label3.setText("Odaberite željenu količinu i kliknite na dugme \"Dodaj u korpu\".");
            label4.setText("Proizvode koje ste dodali u korpu možete pregledati u kartici \"Korpa\".");
            label5.setText("Ukoliko želite obrisati proizvod iz korpe, možete to učiniti klikom na kanta ikonicu pored željenog proizvoda.");
            label6.setText("Ukoliko želite odbaciti kupovinu, korpu možete isprazniti klikom na dugme \"Odbaci kupovinu\".");
            label7.setText("Da biste finalizirali kupovinu, kliknite na dugme \"Završi kupovinu\".");

            image1.setImage(new Image(getClass().getResourceAsStream("/png/help9_bs.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help10_bs.PNG")));
            image4.setImage(new Image(getClass().getResourceAsStream("/png/help12_bs.PNG")));
            image5.setImage(new Image(getClass().getResourceAsStream("/png/help11_bs.PNG")));

        }

        else if(l.getLang().equals("en")){
            label1.setText("Clicking the button \"Add to cart\" opens a new window with detailed product information.");
            label2.setText("Clicking the button \"Add to cart\" opens a new window with detailed product information.");
            label3.setText("Select the desired quantity and click \"Add to cart\".");
            label4.setText("Products that have been previously added to cart can be reviewed in \"Cart\" tab.");
            label5.setText("If you wish to remove a single product from the cart, you can do it by clicking the trash can icon next to the product.");
            label6.setText("Clicking on \"Discard\" removes all the products from the cart.");
            label7.setText("To finalize a purchase, click the button \"Finalize purchase\".");

            image1.setImage(new Image(getClass().getResourceAsStream("/png/help9_en.PNG")));
            image2.setImage(new Image(getClass().getResourceAsStream("/png/help10_en.PNG")));
            image4.setImage(new Image(getClass().getResourceAsStream("/png/help12_en.PNG")));
            image5.setImage(new Image(getClass().getResourceAsStream("/png/help11_en.PNG")));

        }
    }
}
