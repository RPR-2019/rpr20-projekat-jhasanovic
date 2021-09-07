package controller;

import dal.CartDAO;
import dal.ProductDAO;
import dal.SoldProductDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import net.sf.jasperreports.engine.JRException;
import sample.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ControllerMain {

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
    public MenuItem mniBS;
    @FXML
    public MenuItem mniEN;
    @FXML
    public MenuItem mniExit;
    @FXML
    public MenuItem mniHelp2;
    @FXML
    public MenuItem mniAbout;
    @FXML
    public MenuBar menu;
    @FXML
    public Button btnDiscardCart;
    @FXML
    public Button btnFinalize;
    @FXML
    public Button btnRemoveProduct;
    @FXML
    public TableColumn<CartProduct,String> columnIDCart;
    @FXML
    public TableColumn<CartProduct,String> columnNameCart;
    @FXML
    public TableColumn<CartProduct,String> columnQuantityCart;
    @FXML
    public TableColumn<CartProduct,String> columnPriceCart;
    @FXML
    public MenuItem mniCreateAcc;
    @FXML
    public MenuItem mniLogoutBtn;
    @FXML
    TableColumn<CartProduct, Void> colBtn;
    @FXML
    public TableView<CartProduct> tableCart;
    @FXML
    public Tab tabHomepage;
    @FXML
    TabPane tabPane;


    ObservableList<String> opcije = FXCollections.observableArrayList(SearchCriteriaBS.getValues());
    ObservableList<String> options = FXCollections.observableArrayList(SearchCriteriaEN.getValues());
    ObservableList<String> administrationBS = AdministrationMethodBS.getValues();
    ObservableList<String> administrationEN = AdministrationMethodEN.getValues();
    ObservableList<String> typeBS = FXCollections.observableArrayList(MedicationTypeBS.getValues());
    ObservableList<String> typeEN = FXCollections.observableArrayList(MedicationTypeEN.getValues());
    ObservableList<String> purposeBS = FXCollections.observableArrayList(PurposeBS.getValues());
    ObservableList<String> purposeEN = FXCollections.observableArrayList(PurposeEN.getValues());

    @FXML
    public ChoiceBox<String> choiceSearch=new ChoiceBox<>(opcije);
    private ProductDAO dao;
    private CartDAO daoCart;
    private SoldProductDAO daoSold;
    private Language l;
    private CurrentUser user;

    @FXML
    public void initialize() throws SQLException, IncorrectDataException {
        user=CurrentUser.getInstance();
        tabHomepage.isSelected();
        dao=ProductDAO.getInstance();
        daoCart=CartDAO.getInstance();
        daoSold=SoldProductDAO.getInstance();

        ArrayList<CartProduct> cart = new ArrayList<>(daoCart.getProducts());
        cart.forEach((c)->dao.changeQuantity(c.getID(),dao.getQuantity(c.getID())+c.getQuantity()));

        daoCart.emptyOut();//korpa se prazni nakon svakog zatvaranja programa
        tableCart.getSelectionModel().clearSelection();
        new Thread(() -> {
            try {
                while (true) {
                        Platform.runLater(() -> tableCart.setItems(daoCart.getProducts()));
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {

            }
        }).start();

        new Thread(() -> {
            try {
                while (true) {
                    Platform.runLater(() -> {
                        if(user.getUsername().equals("root"))
                            mniCreateAcc.setVisible(true);
                        else
                            mniCreateAcc.setVisible(false);
                    });
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {

            }
        }).start();

        l=Language.getInstance();

        if(l.getLang().equals("bs")) choiceSearch.setItems(opcije);
        else if(l.getLang().equals("en")) choiceSearch.setItems(options);

        choiceSearch.getSelectionModel().select(0);

            columnName.setCellValueFactory(new PropertyValueFactory<>("name")); //s mora biti isto kao naziv atributa u modelu Product
            columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            columnCategory.setCellValueFactory(new PropertyValueFactory<>("purpose"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        productList.setItems(dao.getProducts());

        filter();
        choiceSearch.setOnAction((event) -> {
            searchBar.setText("");
        });

        columnNameCart.setCellValueFactory(new PropertyValueFactory<>("name")); //s mora biti isto kao naziv atributa u modelu Product
        columnIDCart.setCellValueFactory(new PropertyValueFactory<>("ID"));
        columnPriceCart.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantityCart.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        addButtonToTable();
        tableCart.setItems(daoCart.getProducts());

        }

    public void filter() throws IncorrectDataException {
        FilteredList<Product> filteredProducts=new FilteredList<>(dao.getProducts(),b->true);
        searchBar.textProperty().addListener((observable,oldValue,newValue )-> {
            filteredProducts.setPredicate(p->{
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter=newValue.toLowerCase();
                if(choiceSearch.getValue().equals("Po nazivu") &&
                        p.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                if(choiceSearch.getValue().equals("Po šifri") &&
                        p.getID().toString().contains(lowerCaseFilter)) return true;
                if(choiceSearch.getValue().equals("Po namjeni") && p.getPurpose()!=null &&
                        p.getPurpose().toLowerCase().contains(lowerCaseFilter)) return true;
                else return false;
            });
        });

        SortedList<Product> sortedData=new SortedList<>(filteredProducts);
        sortedData.comparatorProperty().bind(productList.comparatorProperty());
        productList.setItems(sortedData);
    }

    private void addButtonToTable() {

        Callback<TableColumn<CartProduct, Void>, TableCell<CartProduct, Void>> cellFactory = new Callback<TableColumn<CartProduct, Void>, TableCell<CartProduct, Void>>() {
            @Override
            public TableCell<CartProduct, Void> call(final TableColumn<CartProduct, Void> param) {
                final TableCell<CartProduct, Void> cell = new TableCell<CartProduct, Void>() {

                    private final Button btn = new Button("");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            removeCartBtnClick(getTableView().getItems().get(getIndex()));

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                            btn.setGraphic(new ImageView("img/trash.png"));
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
    }


    public void addBtnClick(ActionEvent actionEvent) throws Exception{
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"),bundle);
        Parent root = loader.load();
        ControllerAddProduct addProduct = loader.getController();

        if(l.getLang().equals("bs")){
            myStage.setTitle("Dodavanje novog proizvoda");
            addProduct.choiceAdMethod.setItems(administrationBS.sorted());
            addProduct.choiceType.setItems(typeBS.sorted());
            addProduct.choicePurpose.setItems(purposeBS.sorted());
        }

        else if(l.getLang().equals("en")){
            myStage.setTitle("Adding a new product");
            addProduct.choiceAdMethod.setItems(administrationEN.sorted());
            addProduct.choiceType.setItems(typeEN.sorted());
            addProduct.choicePurpose.setItems(purposeEN.sorted());
        }

        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                productList.getSelectionModel().clearSelection();
                try {
                    productList.setItems(dao.getProducts());
                } catch (IncorrectDataException e) {
                    e.printStackTrace();
                }
                try {
                    filter();
                } catch (IncorrectDataException e) {
                    e.printStackTrace();
                }
            }
        });
        myStage.initModality(Modality.WINDOW_MODAL);
        myStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        myStage.setResizable(false);
        myStage.show();
    }

    public void updateBtnClick(ActionEvent actionEvent) throws IOException {
        if(productList.getSelectionModel().getSelectedItem()!=null && daoCart.getProductCount(productList.getSelectionModel().getSelectedItem().getID())==0) {
            Stage myStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/updateProduct.fxml"),bundle);
            Parent root = loader.load();
            ControllerUpdateProduct updateProduct = loader.getController();

            updateProduct.setIndex(productList.getSelectionModel().getSelectedItem().getID());
            updateProduct.fldID.setText(String.valueOf(productList.getSelectionModel().getSelectedItem().getID()));
            updateProduct.fldName.setText(productList.getSelectionModel().getSelectedItem().getName());
            updateProduct.fldPrice.setText(String.valueOf(productList.getSelectionModel().getSelectedItem().getPrice()));
            updateProduct.fldQuantity.setText(String.valueOf(productList.getSelectionModel().getSelectedItem().getQuantity()));
            updateProduct.choicePurpose.setValue(productList.getSelectionModel().getSelectedItem().getPurpose());
            updateProduct.fldNotes.setText(productList.getSelectionModel().getSelectedItem().getNotes());
            updateProduct.choiceAdMethod.setValue(productList.getSelectionModel().getSelectedItem().getAdministrationMethod());
            updateProduct.fldManufacturer.setText(productList.getSelectionModel().getSelectedItem().getManufacturer());
            updateProduct.fldDescription.setText(productList.getSelectionModel().getSelectedItem().getDescription());
            updateProduct.fldIngredients.setText(productList.getSelectionModel().getSelectedItem().getIngredients());
            updateProduct.choiceType.setValue(productList.getSelectionModel().getSelectedItem().getMedicationType());


            if(l.getLang().equals("bs")){
                myStage.setTitle("Ažuriranje proizvoda");
                updateProduct.choiceAdMethod.setItems(administrationBS.sorted());
                updateProduct.choiceAdMethod.getSelectionModel().select(productList.getSelectionModel().getSelectedItem().getAdministrationMethod());

                updateProduct.choiceType.setItems(typeBS.sorted());
                updateProduct.choiceType.getSelectionModel().select(productList.getSelectionModel().getSelectedItem().getMedicationType());

                updateProduct.choicePurpose.setItems(purposeBS.sorted());
                updateProduct.choicePurpose.getSelectionModel().select(productList.getSelectionModel().getSelectedItem().getPurpose());
            }

            else if(l.getLang().equals("en")){
                myStage.setTitle("Updating a product");
                updateProduct.choiceAdMethod.setItems(administrationEN.sorted());
                updateProduct.choiceAdMethod.getSelectionModel().select(productList.getSelectionModel().getSelectedItem().getAdministrationMethod());

                updateProduct.choiceType.setItems(typeEN.sorted());
                updateProduct.choiceType.getSelectionModel().select(productList.getSelectionModel().getSelectedItem().getMedicationType());

                updateProduct.choicePurpose.setItems(purposeEN.sorted());
                updateProduct.choicePurpose.getSelectionModel().select(productList.getSelectionModel().getSelectedItem().getPurpose());
            }
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    productList.getSelectionModel().clearSelection();
                    try {
                        productList.setItems(dao.getProducts());
                    } catch (IncorrectDataException e) {
                        e.printStackTrace();
                    }
                    try {
                        filter();
                    } catch (IncorrectDataException e) {
                        e.printStackTrace();
                    }
                }
            });
            myStage.initModality(Modality.WINDOW_MODAL);
            myStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            myStage.setResizable(false);
            myStage.show();
            searchBar.setText("");
        }
        else if(productList.getSelectionModel().getSelectedItem()!=null){
            //proizvod koji je već u korpi nije moguće ažurirati da se izbjegnu nekonzistentni podaci
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if(l.getLang().equals("bs")) {
                    alert.setTitle("Upozorenje");
                    alert.setHeaderText("Ažuriranje proizvoda koji je dodan u korpu nije moguće!");
                    alert.setContentText("Molimo obrišite proizvod iz korpe prije ažuriranja.");
                }
                if(l.getLang().equals("en")) {
                    alert.setTitle("Warning");
                    alert.setHeaderText("Updating a product already added to cart is not allowed!");
                    alert.setContentText("Please remove the product you want to update from the cart.");
                }
                alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);

            if(l.getLang().equals("bs")) {
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Niste odabrali proizvod!");
                alert.setContentText("Molimo odaberite proizvod iz tabele koji želite ažurirati.");
            }
            else if(l.getLang().equals(("en"))){
                alert.setTitle("Warning");
                alert.setHeaderText("Product not selected!");
                alert.setContentText("Please select a product you wish to update from the table.");
            }
            alert.showAndWait();
        }
    }

    public void addToCartBtnClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/productInfo.fxml"),bundle);
        Parent root = loader.load();
        ControllerProductInfo productInfo = loader.getController();

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
                productInfo.idFld.setText(String.valueOf(productList.getSelectionModel().getSelectedItem().getID()));
                productInfo.typeFld.setText(productList.getSelectionModel().getSelectedItem().getPurpose());
                productInfo.quantityFld.setText("1");
                productInfo.totalFld.setText((String.format("%.2f",productList.getSelectionModel().getSelectedItem().getPrice())) + " KM");

                productInfo.manufacturerFld.setText(productList.getSelectionModel().getSelectedItem().getManufacturer());
                productInfo.purposeFld.setText(productList.getSelectionModel().getSelectedItem().getPurpose());
                productInfo.administrationFld.setText(productList.getSelectionModel().getSelectedItem().getAdministrationMethod());
                productInfo.notesFld.setText(productList.getSelectionModel().getSelectedItem().getNotes());
                productInfo.descriptionFld.setText(productList.getSelectionModel().getSelectedItem().getDescription());
                productInfo.ingredientsFld.setText(productList.getSelectionModel().getSelectedItem().getIngredients());

                if(l.getLang().equals("bs")) myStage.setTitle("Podaci o proizvodu");
                else if(l.getLang().equals("en")) myStage.setTitle("Product info");
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.initModality(Modality.WINDOW_MODAL);
                myStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
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

        myStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                productList.getSelectionModel().clearSelection();
                try {
                    productList.setItems(dao.getProducts());
                } catch (IncorrectDataException e) {
                    e.printStackTrace();
                }
                try {
                    filter();
                } catch (IncorrectDataException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void bsMenuClick(ActionEvent actionEvent) throws IOException {
        //otvoriti prozor opet
        if(!l.getLang().equals("bs")) {
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.close();

            l.setLang("bs");
            Locale.setDefault(new Locale("bs", "BA"));
            Stage myStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"), bundle);
            myStage.setTitle("Apoteka");
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
        }

    }

    public void enMenuClick(ActionEvent actionEvent) throws IOException {
        if(!l.getLang().equals("en")) {
            Stage stage = (Stage) menu.getScene().getWindow();
            stage.close();

            l.setLang("en");
            Locale.setDefault(new Locale("en", "UK"));
            Stage myStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainWindow.fxml"), bundle);
            myStage.setTitle("Pharmacy");
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.show();
        }
    }

    public void aboutMenuClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"),bundle);
        if(l.getLang().equals("bs")) myStage.setTitle("O aplikaciji");
        else if(l.getLang().equals("en")) myStage.setTitle("About app");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setResizable(false);
        myStage.show();
    }

    public void discardCartClick(ActionEvent actionEvent) {
        //vratiti kolicine proizvoda na staro stanje:
        ArrayList<CartProduct> cart = new ArrayList<>(daoCart.getProducts());
        cart.forEach((c)->dao.changeQuantity(c.getID(),dao.getQuantity(c.getID())+c.getQuantity()));
        //isprazniti korpu
        daoCart.emptyOut();
        tableCart.getSelectionModel().clearSelection();
        tableCart.setItems(daoCart.getProducts());
    }

    public void finalizeBtnClick(ActionEvent actionEvent) {
        ArrayList<CartProduct> cart = daoCart.getProductsArrayList();
        CurrentUser user=CurrentUser.getInstance();
        cart.forEach((c)-> {
            int max = daoSold.getMaxID();
            daoSold.dodajProdani(new SoldProduct(max + 1, c.getID(), c.getName(), c.getQuantity(), user.getUsername(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        });

if(daoCart.getCartSize()>0) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    if (l.getLang().equals("bs")) {
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Da");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Ne");
        alert.setTitle("Račun");
        alert.setHeaderText("");
        alert.setContentText("Da li želite prikazati račun?");
    } else if (l.getLang().equals("en")) {
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
        alert.setTitle("Receipt");
        alert.setHeaderText("");
        alert.setContentText("Do you want to show the receipt?");
    }
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("total", daoCart.getTotal());
            if (l.getLang().equals("bs"))
                new PrintReport().showReport(SqliteHelper.getConn(), "Receipt_bs", map);
            else if (l.getLang().equals("en"))
                new PrintReport().showReport(SqliteHelper.getConn(), "Receipt_en", map);
        } catch (JRException | SQLException e1) {
            e1.printStackTrace();
        }
    }
}
else{
    Alert alert = new Alert(Alert.AlertType.WARNING);
    if(l.getLang().equals("en")) {
        alert.setTitle("Warning");
        alert.setHeaderText("Cart empty!");
        alert.setContentText("You have not added any products to the cart.");
    }
    else if(l.getLang().equals("bs")){
        alert.setTitle("Upozorenje");
        alert.setHeaderText("Korpa prazna!");
        alert.setContentText("Niste dodali nijedan proizvod u korpu.");
    }
    alert.showAndWait();
}
        daoCart.emptyOut();
        tableCart.getSelectionModel().clearSelection();
        tableCart.setItems(daoCart.getProducts());
    }

    public void removeBtnClick(ActionEvent actionEvent) throws IncorrectDataException {
        if(productList.getSelectionModel().getSelectedItem()!=null) {
            dao.removeProduct(productList.getSelectionModel().getSelectedItem());
            productList.getSelectionModel().clearSelection();
            productList.setItems(dao.getProducts());
            searchBar.setText("");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(l.getLang().equals("bs")) {
                alert.setTitle("Upozorenje");
                alert.setHeaderText("Niste odabrali proizvod!");
                alert.setContentText("Za brisanje proizvoda odaberite proizvod iz liste");
            }
            if(l.getLang().equals("en")) {
                alert.setTitle("Warning");
                alert.setHeaderText("No product is selected!");
                alert.setContentText("Select a product you wish to remove from the list");
            }
            alert.showAndWait();
        }
    }

    public void helpMenuClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/help.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.setMinWidth(740);
        myStage.setMinHeight(480);
        if(l.getLang().equals("bs")) myStage.setTitle("Pomoć");
        else if(l.getLang().equals("en")) myStage.setTitle("Help");
        myStage.show();
    }

    public void exitMenuClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void removeCartBtnClick(CartProduct p) {
            daoCart.removeProduct(p);
            //vratiti stanje na staro
            dao.changeQuantity(p.getID(),p.getQuantity()+dao.getQuantity(p.getID()));
    }

    public void mniCreateAccountClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        myStage.setResizable(false);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/createAccount.fxml"), bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        if(l.getLang().equals("bs")) myStage.setTitle("Kreiraj novi korisnički račun");
        else if(l.getLang().equals("en")) myStage.setTitle("Create new user account");
        myStage.initModality(Modality.APPLICATION_MODAL);
        myStage.show();
    }

    public void mniLogoutClick(ActionEvent actionEvent) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(l.getLang().equals("bs")){
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Odjavi se");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Odustani");
            alert.setTitle("Logout");
            alert.setHeaderText("Potvrdite odjavu");
            alert.setContentText("Da li ste sigurni da se želite odjaviti?");
        }
        else if(l.getLang().equals("en")){
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Log out");
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
            alert.setTitle("Logout");
            alert.setHeaderText("Logout confirmation");
            alert.setContentText("Are you sure you want to log out?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = new Stage();
            Window window = ((MenuItem)actionEvent.getTarget()).getParentPopup().getOwnerWindow();
            Stage thisStage = (Stage) window.getScene().getWindow();
            thisStage.close();

            Stage myStage = new Stage();
            myStage.setResizable(false);
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            if(l.getLang().equals("bs")) myStage.setTitle("Prijava");
            else if(l.getLang().equals("en")) myStage.setTitle("Login");
            myStage.show();
        }
    }

    public void mniReportClick(ActionEvent actionEvent) throws IOException {
        Stage myStage = new Stage();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/reportChoice.fxml"),bundle);
        if(l.getLang().equals("bs")) myStage.setTitle("Odabir tipa izvještaja");
        else if(l.getLang().equals("en")) myStage.setTitle("Report type choice");
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.initModality(Modality.APPLICATION_MODAL);
        myStage.setResizable(false);
        myStage.show();
    }

    public void onCartUnselected(Event event) throws IncorrectDataException {
        productList.getSelectionModel().clearSelection();
        productList.setItems(dao.getProducts());
    }
}