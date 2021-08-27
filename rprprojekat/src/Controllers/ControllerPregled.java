package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerPregled {

    @FXML
    public TableView<Product> productList;
    @FXML
    public TableColumn<Product, String> columnName;
    @FXML
    public TableColumn<Product, String> columnID;
    @FXML
    public TableColumn<Product, String> columnCategory;
    @FXML
    public TableColumn<Product, String> columnPrice;
    @FXML
    public TableColumn<Product, String> columnQuantity;

    @FXML
    public Button btnAddProduct;
    @FXML
    public Button btnUpdateProduct;

    private PharmacyModel model;

    public ControllerPregled(){
        model=new PharmacyModel();
        model.napuni();
    }
    @FXML
    public void initialize(){
            columnName.setCellValueFactory(new PropertyValueFactory<>("name")); //s mora biti isto kao naziv atributa u modelu Product
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            productList.setItems(model.getProducts());
        }
}
