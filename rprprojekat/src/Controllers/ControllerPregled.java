package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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
    @FXML
    public TextField searchBar;
    @FXML
    public Button addToCartBtn;

    ObservableList<String> options = FXCollections.observableArrayList("Po nazivu","Po šifri");
    @FXML
    public ChoiceBox<String> choiceSearch=new ChoiceBox<>(options);

    private PharmacyModel model;

    public ControllerPregled(){
        model=new PharmacyModel();
        model.napuni();
    }
    @FXML
    public void initialize(){
        choiceSearch.setItems(options);
        choiceSearch.getSelectionModel().select(0);

            columnName.setCellValueFactory(new PropertyValueFactory<>("name")); //s mora biti isto kao naziv atributa u modelu Product
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            productList.setItems(model.getProducts());

        FilteredList<Product> filteredProducts=new FilteredList<>(model.getProducts(),b->true);
        searchBar.textProperty().addListener((observable,oldValue,newValue )-> {
            filteredProducts.setPredicate(p->{
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter=newValue.toLowerCase();
                if(choiceSearch.getValue().equals("Po nazivu") &&
                        p.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                if(choiceSearch.getValue().equals("Po šifri") &&
                        p.getID().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });

        choiceSearch.setOnAction((event) -> {
            System.out.println("   ChoiceBox.getValue(): " + choiceSearch.getValue());
            searchBar.setText("");
        });

        SortedList<Product> sortedData=new SortedList<>(filteredProducts);
        sortedData.comparatorProperty().bind(productList.comparatorProperty());
        productList.setItems(sortedData);
        }


    public void addBtnClick(ActionEvent actionEvent) throws Exception{
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dodajProizvod.fxml"));
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void updateBtnClick(ActionEvent actionEvent) {

    }

    public void cartBtnClick(ActionEvent actionEvent) {
    }

    public void addToCartBtnClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/productInfo.fxml"));
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }
}
