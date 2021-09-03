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
import sample.Language;
import sample.Product;
import sample.ProductDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerAddProduct implements Initializable {

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
    public void btnAddClick(ActionEvent actionEvent) throws SQLException {
        String a1=fldName.getText();

        try {
            Integer a2 = Integer.parseInt(fldID.getText());
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
            dao.addProduct(p);
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Neispravni podaci!");
                String greska="Mogući uzroci greške: \n";
                    greska = greska + "Kod proizvoda mora biti cijeli broj!\n";
                    greska = greska + "Molimo unesite cijenu proizvoda u formatu sa tačkom!\n";
                    greska = greska + "Količina proizvoda mora biti cijeli broj!\n";
                alert.setContentText(greska);
            }
            else if(l.getLang().equals("en")){
                    alert.setTitle("Error");
                    alert.setHeaderText("Incorrect data input!");
                    String greska="Possible error causes: \n";
                        greska = greska + "Product ID has to be an integer!\n";
                        greska = greska + "Please enter the price with dot instead of a comma!\n";
                        greska = greska + "Product quantity has to be an integer!\n";
                    alert.setContentText(greska);
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
    public void keyReleased(KeyEvent keyEvent) {
        if(fldID.getText().trim().isEmpty() || fldName.getText().trim().isEmpty() || fldQuantity.getText().trim().isEmpty() ||
        fldPrice.getText().trim().isEmpty())
            btnAdd.setDisable(true);
        else btnAdd.setDisable(false);
    }
}
