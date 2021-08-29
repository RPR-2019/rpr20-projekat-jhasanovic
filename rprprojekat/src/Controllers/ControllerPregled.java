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
import javafx.scene.layout.BorderPane;
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
    @FXML
    public BorderPane mainWindow;
    @FXML
    public Button cartBtn;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productInfo.fxml"));
        Parent root = loader.load();
        ProductInfoController productInfo = loader.getController();

        if(productList.getSelectionModel().getSelectedItem()!=null) {
            if (productList.getSelectionModel().getSelectedItem().getQuantity()==0) {
                //proizvod nije dostupan, ali nije ni obrisan u slucaju ako bi se kasnije azurirao
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška");
                alert.setHeaderText("Proizvod nije na stanju!");
                alert.setContentText("Odabrani proizvod trenutno nije dostupan.");

                alert.showAndWait();
            } else {
                //slanje kolicine u drugi kontroler
                productInfo.maxQuantity(productList.getSelectionModel().getSelectedItem().getQuantity());
                productInfo.getPrice(productList.getSelectionModel().getSelectedItem().getPrice());

                productInfo.nameFld.setText(productList.getSelectionModel().getSelectedItem().getName());
                productInfo.idFld.setText(productList.getSelectionModel().getSelectedItem().getID());
                productInfo.categoryFld.setText(productList.getSelectionModel().getSelectedItem().getCategory());
                productInfo.quantityFld.setText("1");
                productInfo.totalFld.setText (productList.getSelectionModel().getSelectedItem().getPrice() + " KM");
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.setResizable(false);
                myStage.show();
            }
        }
    }
}
