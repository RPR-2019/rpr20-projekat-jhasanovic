package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAddProduct implements Initializable {

    @FXML
    public TextField fieldName;
    @FXML
    public TextField fieldID;
    @FXML
    public TextField fieldPrice;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnCancel;

    ObservableList<String> options = FXCollections.observableArrayList("Tablete","Sirup");
    @FXML
    public ChoiceBox<String> choiceCategory=new ChoiceBox<>(options);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceCategory.setItems(options);
        choiceCategory.getSelectionModel().select(0);
    }


    public void btnAddClick(ActionEvent actionEvent) {

    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
