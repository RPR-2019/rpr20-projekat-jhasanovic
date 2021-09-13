package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.IncorrectDataException;
import ba.unsa.etf.rpr.Language;
import ba.unsa.etf.rpr.UniqueIdException;
import ba.unsa.etf.rpr.beans.Product;
import ba.unsa.etf.rpr.dal.ProductDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    public TextField fldName;
    @FXML
    public TextField fldID;
    @FXML
    public TextField fldPrice;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnCancel;
    @FXML
    public TextField fldManufacturer;
    @FXML
    public TextArea fldIngredients;
    @FXML
    public TextArea fldNotes;
    @FXML
    public ComboBox<String> choicePurpose;
    @FXML
    public ComboBox<String> choiceType;
    @FXML
    public ComboBox<String> choiceAdMethod;
    @FXML
    public TextField fldQuantity;
    @FXML
    public TextArea fldDescription;

    private ProductDAO dao;
    private Language l = Language.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setDisable(true);

        try {
            dao = ProductDAO.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setFieldColor(fldID);
        setFieldColor(fldName);
        setFieldColor(fldQuantity);
        setFieldColor(fldPrice);
    }

    private void setFieldColor(TextField field) {
        field.getStyleClass().add("emptyField");
        field.textProperty().addListener((observableValue, o, n) -> {
            if (field.getText().trim().isEmpty()) {
                field.getStyleClass().removeAll("nonEmptyField");
                field.getStyleClass().add("emptyField");
            } else {
                field.getStyleClass().removeAll("emptyField");
                field.getStyleClass().add("nonEmptyField");
            }
        });
    }

    @FXML
    public void btnAddClick(ActionEvent actionEvent) {
        try {
            validateRequiredFields(fldID.getText(), fldQuantity.getText(), fldPrice.getText());
            Product p = new Product(fldName.getText(), Integer.parseInt(fldID.getText()), Double.parseDouble(fldPrice.getText()),
                    Integer.parseInt(fldQuantity.getText()), choicePurpose.getValue(), fldNotes.getText(), choiceAdMethod.getValue(),
                    fldManufacturer.getText(), fldDescription.getText(), fldIngredients.getText(), choiceType.getValue());
            try {
                dao.addProduct(p);
                Node n = (Node) actionEvent.getSource();
                Stage stage = (Stage) n.getScene().getWindow();
                stage.close();
            } catch (UniqueIdException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                if(l.getLang().equals("bs")) {
                    alert.setTitle("Greška");
                    alert.setHeaderText("Neuspjelo dodavanje proizvoda!");
                    alert.setContentText(e.getMessage());
                }
                else if(l.getLang().equals("en")){
                    alert.setTitle("Error");
                    alert.setHeaderText("Adding a new product failed!");
                    alert.setContentText(e.getMessage());
                }
                alert.showAndWait();
            }

        }
        catch(IncorrectDataException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Neispravni podaci!");
                alert.setContentText(e.getMessage());
            }
            else if(l.getLang().equals("en")) {
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect data input!");
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
        }
    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    //nije moguce dodati proizvod ako nisu popunjena sva required polja
    public void keyReleased() {
        btnAdd.setDisable(fldID.getText().trim().isEmpty() || fldName.getText().trim().isEmpty() || fldQuantity.getText().trim().isEmpty() ||
                fldPrice.getText().trim().isEmpty());
    }

    public void validateRequiredFields(Object id,Object quantity,Object price) throws IncorrectDataException {
        try {
            Integer.parseInt(id.toString());
        }
        catch(NumberFormatException e){
            String message="";
            if(l.getLang().equals("bs"))
                message="Kod lijeka mora biti cijeli broj!";
            else if(l.getLang().equals("en"))
                message="Product ID has to be an integer!";
            throw new IncorrectDataException(message);
        }
        try {
            Double.parseDouble(price.toString());
        }
        catch(NumberFormatException e){
            String message="";
            if(l.getLang().equals("bs"))
                message="Cijena lijeka mora biti realni broj!";
            else if(l.getLang().equals("en"))
                message="Product price has to be a real number!";
            throw new IncorrectDataException(message);
        }
        try {
            Integer.parseInt(quantity.toString());
        }
        catch(NumberFormatException e){
            String message="";
            if(l.getLang().equals("bs"))
                message="Količina lijeka mora biti cijeli broj!";
            else if(l.getLang().equals("en"))
                message="Product quantity has to be an integer!";
            throw new IncorrectDataException(message);
        }
    }
}
