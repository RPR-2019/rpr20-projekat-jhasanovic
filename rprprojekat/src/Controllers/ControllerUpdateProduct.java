package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerUpdateProduct {

    @FXML
    public TextField fieldName2;
    @FXML
    public TextField fieldID2;
    @FXML
    public TextField fieldPrice2;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnCancel2;
    @FXML
    public ChoiceBox<String> choiceCategory2;
    
    public void btnCancelClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateClick(ActionEvent actionEvent) {
    }
}
