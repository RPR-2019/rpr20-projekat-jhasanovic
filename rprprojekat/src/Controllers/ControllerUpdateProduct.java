package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.IncorrectDataException;
import sample.Language;
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


    private ProductDAO dao;
    private Language l = Language.getInstance();

    private Integer index;

    public void setIndex(Integer id) {
        index=id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            dao=ProductDAO.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        fldID.getStyleClass().add("emptyField");
        fldID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (fldID.getText().trim().isEmpty()) {
                    fldID.getStyleClass().removeAll("nonEmptyField");
                    fldID.getStyleClass().add("emptyField");
                } else {
                    fldID.getStyleClass().removeAll("emptyField");
                    fldID.getStyleClass().add("nonEmptyField");
                }
            }
        });
        fldName.getStyleClass().add("emptyField");
        fldName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (fldName.getText().trim().isEmpty()) {
                    fldName.getStyleClass().removeAll("nonEmptyField");
                    fldName.getStyleClass().add("emptyField");
                } else {
                    fldName.getStyleClass().removeAll("emptyField");
                    fldName.getStyleClass().add("nonEmptyField");
                }
            }
        });
        fldQuantity.getStyleClass().add("emptyField");
        fldQuantity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (fldQuantity.getText().trim().isEmpty()) {
                    fldQuantity.getStyleClass().removeAll("nonEmptyField");
                    fldQuantity.getStyleClass().add("emptyField");
                } else {
                    fldQuantity.getStyleClass().removeAll("emptyField");
                    fldQuantity.getStyleClass().add("nonEmptyField");
                }
            }
        });
        fldPrice.getStyleClass().add("emptyField");
        fldPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (fldPrice.getText().trim().isEmpty()) {
                    fldPrice.getStyleClass().removeAll("nonEmptyField");
                    fldPrice.getStyleClass().add("emptyField");
                } else {
                    fldPrice.getStyleClass().removeAll("emptyField");
                    fldPrice.getStyleClass().add("nonEmptyField");
                }
            }
        });
    }

    @FXML
    public void btnUpdateClick(ActionEvent actionEvent) throws IncorrectDataException {
        try {
            validateRequiredFields(fldID.getText(),fldQuantity.getText(),fldPrice.getText());
            Product p = new Product(fldName.getText(),Integer.parseInt(fldID.getText()),Double.parseDouble(fldPrice.getText()),
                    Integer.parseInt(fldQuantity.getText()),choicePurpose.getValue(),fldNotes.getText(),choiceAdMethod.getValue(),
                    fldManufacturer.getText(),fldDescription.getText(),fldIngredients.getText(),choiceType.getValue());
            dao.updateProduct(p, index);

            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }
        catch(IncorrectDataException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Neispravni podaci!");
                alert.setContentText(e.getMessage());
            }
            else if(l.getLang().equals("en")){
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


    //nije moguce azurirati proizvod ako nisu popunjena sva required polja
    public void keyReleased(KeyEvent keyEvent) {
        if(fldID.getText().trim().isEmpty() || fldName.getText().trim().isEmpty() || fldQuantity.getText().trim().isEmpty() ||
                fldPrice.getText().trim().isEmpty())
            btnUpdate.setDisable(true);
        else btnUpdate.setDisable(false);
    }

    public void validateRequiredFields(Object id,Object quantity,Object price) throws IncorrectDataException {
        try {
            Integer x = Integer.parseInt(id.toString());
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
            Double z = Double.parseDouble(price.toString());
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
            Integer y = Integer.parseInt(quantity.toString());
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
