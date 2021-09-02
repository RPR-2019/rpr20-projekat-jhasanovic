package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Product;
import sample.ProductDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerUpdateProduct implements Initializable {

    @FXML
    public TextField fldName;
    @FXML
    public TextField fldID;
    @FXML
    public TextField fldPrice;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnCancel;
    @FXML
    public ComboBox<String> choicePurpose;
    @FXML
    public ComboBox<String> choiceType;
    @FXML
    public ComboBox<String> choiceAdMethod;
    @FXML
    public TextField fldQuantity;
    @FXML
    public TextArea fldIngredients;
    @FXML
    public TextArea fldNotes;
    @FXML
    public TextField fldManufacturer;
    @FXML
    public TextArea fldDescription;

    ObservableList<String> options = FXCollections.observableArrayList("Tablete","Sirup");
    @FXML
    public ChoiceBox<String> choiceCategory2=new ChoiceBox<>(options);

    private ProductDAO dao;
    private String index;

    public void setIndex(String id) {
        index=id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceCategory2.setItems(options);
        choiceCategory2.getSelectionModel().select(0);

        try {
            dao=ProductDAO.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void btnUpdateClick(ActionEvent actionEvent) throws SQLException {
        String a1=fldName.getText();
        String a2=fldID.getText();
        Double a3=Double.parseDouble(fldPrice.getText());
        Integer a4=Integer.parseInt(fldQuantity.getText());
        String a5=choicePurpose.getValue();
        String a6=fldNotes.getText();
        String a7=choiceAdMethod.getValue();
        String a8=fldManufacturer.getText();
        String a9=fldDescription.getText();
        String a10=fldIngredients.getText();
        String a11=choiceType.getValue();
        Product p = new Product(a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11);
        dao.updateProduct(p,index);

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }


}
