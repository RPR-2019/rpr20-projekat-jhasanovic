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
import sample.Language;
import sample.Product;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
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
    public MenuItem bsMenu;
    @FXML
    public MenuItem enMenu;
    @FXML
    public MenuBar menu;

    ObservableList<String> opcije = FXCollections.observableArrayList("Po nazivu","Po šifri");
    ObservableList<String> options = FXCollections.observableArrayList("By category","By ID");

    @FXML
    public ChoiceBox<String> choiceSearch=new ChoiceBox<>(opcije);

    private PharmacyModel model;
    Language l = Language.getInstance();

    public ControllerPregled(){
        model=new PharmacyModel();
        model.napuni();
    }

    @FXML
    public void initialize(){
        if(l.getLang().equals("bs")) choiceSearch.setItems(opcije);
        else if(l.getLang().equals("en")) choiceSearch.setItems(options);

        choiceSearch.getSelectionModel().select(0);

            columnName.setCellValueFactory(new PropertyValueFactory<>("name")); //s mora biti isto kao naziv atributa u modelu Product
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnCategory.setCellValueFactory(new PropertyValueFactory<>("purpose"));
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
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dodajProizvod.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void updateBtnClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/azurirajProizvod.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void addToCartBtnClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productInfo.fxml"),bundle);
        Parent root = loader.load();
        ProductInfoController productInfo = loader.getController();

        if(productList.getSelectionModel().getSelectedItem()!=null) {
            if (productList.getSelectionModel().getSelectedItem().getQuantity()==0) {
                //proizvod nije dostupan, ali nije ni obrisan u slucaju ako bi se kasnije azurirao
                Alert alert = new Alert(Alert.AlertType.ERROR);

                if(l.getLang().equals("bs")) {
                    alert.setTitle("Greška");
                    alert.setHeaderText("Proizvod nije na stanju!");
                    alert.setContentText("Odabrani proizvod trenutno nije dostupan.");
                }
                else if(l.getLang().equals(("en"))){
                    alert.setTitle("Error");
                    alert.setHeaderText("Product not available!");
                    alert.setContentText("Selected product is currently not available.");
                }
                alert.showAndWait();
            } else {

                //slanje kolicine u drugi kontroler
                productInfo.maxQuantity(productList.getSelectionModel().getSelectedItem().getQuantity());
                productInfo.getPrice(productList.getSelectionModel().getSelectedItem().getPrice());

                productInfo.nameFld.setText(productList.getSelectionModel().getSelectedItem().getName());
                productInfo.idFld.setText(productList.getSelectionModel().getSelectedItem().getID());
                productInfo.typeFld.setText(productList.getSelectionModel().getSelectedItem().getPurpose());
                productInfo.quantityFld.setText("1");
                productInfo.totalFld.setText (productList.getSelectionModel().getSelectedItem().getPrice() + " KM");

                productInfo.manufacturerFld.setText(productList.getSelectionModel().getSelectedItem().getManufacturer());
                productInfo.purposeFld.setText(productList.getSelectionModel().getSelectedItem().getPurpose());
                productInfo.administrationFld.setText(productList.getSelectionModel().getSelectedItem().getAdministrationMethod());
                productInfo.notesFld.setText(productList.getSelectionModel().getSelectedItem().getNotes());
                productInfo.descriptionFld.setText(productList.getSelectionModel().getSelectedItem().getDescription());
                productInfo.ingredientsFld.setText(productList.getSelectionModel().getSelectedItem().getIngredients());

                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.setResizable(false);
                myStage.show();
            }
        }
        else if(productList.getSelectionModel().getSelectedItem()==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(l.getLang().equals("bs")) {
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Niste odabrali proizvod!");
                alert.setContentText("Za dodavanje proizvoda u korpu odaberite proizvod iz liste");
            }
            if(l.getLang().equals("en")) {
                alert.setTitle("Warning");
                alert.setHeaderText("No product is selected!");
                alert.setContentText("Select a product from the list to add to cart");
            }
            alert.showAndWait();
        }
    }

    public void bsMenuClick(ActionEvent actionEvent) throws IOException {
        //otvoriti prozor opet
        Stage stage = (Stage) menu.getScene().getWindow();
        stage.close();

        l.setLang("bs");
        Locale.setDefault(new Locale("bs", "BA"));
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pregledProizvoda.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void enMenuClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) menu.getScene().getWindow();
        stage.close();

        l.setLang("en");
        Locale.setDefault(new Locale("en", "US"));
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pregledProizvoda.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();

    }

    public void aboutMenuClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }
}
