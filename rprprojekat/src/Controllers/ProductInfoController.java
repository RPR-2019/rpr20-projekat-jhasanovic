package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductInfoController {

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
    public Button cancelBtn;

    public void addToCartBtnClick(ActionEvent actionEvent) {
    }

    public void minusBtnClick(ActionEvent actionEvent) {
    }

    public void quantityFld(ActionEvent actionEvent) {
    }

    public void plusBtnClick(ActionEvent actionEvent) {
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
