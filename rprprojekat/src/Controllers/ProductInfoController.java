package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductInfoController implements Initializable {

    @FXML
    public Label nameFld;
    @FXML
    public Label idFld;
    @FXML
    public Label categoryFld;
    @FXML
    public Button minusBtn;
    @FXML
    public TextField quantityFld;
    @FXML
    public Button plusBtn;
    @FXML
    public Label totalFld;
    @FXML
    public Button addToCartBtn;
    @FXML
    public Button cancelBtn;

    private Integer maxQt;
    private Integer price;

    public void maxQuantity(int max){
        maxQt = max;
    }

    public void getPrice(int p){
        price=p;
    }
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        totalFld.textProperty().bind(quantityFld.textProperty());
    }

    public void addToCartBtnClick(ActionEvent actionEvent) {

    }

    public void minusBtnClick(ActionEvent actionEvent) {
        int qt = Integer.parseInt(quantityFld.getText());
        if(qt>1) {
            qt = qt - 1;
            quantityFld.setText(Integer.toString(qt));
        }
    }

    public void plusBtnClick(ActionEvent actionEvent) {
        int qt = Integer.parseInt(quantityFld.getText());
        qt = qt + 1;
        if(qt<=maxQt)
        quantityFld.setText(Integer.toString(qt));
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
