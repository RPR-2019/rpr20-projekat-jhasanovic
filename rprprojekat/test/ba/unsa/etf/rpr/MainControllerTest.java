package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dal.ProductDAO;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {

    @BeforeEach
    public void before() throws SQLException {
        ProductDAO dao = ProductDAO.getInstance();
        dao.DefaultDatabase();
    }

    @Start
    public void start(Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"), bundle);
        Parent root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void addBtnClickAndCancel(FxRobot robot) {
        Button add = robot.lookup("#btnAddProduct").queryAs(Button.class);
        robot.clickOn(add);
        Button cancel = robot.lookup("#btnCancel").queryAs(Button.class);
        robot.clickOn(cancel);
        //zatvorit ce se modalni prozor i moci ce se kliknuti nesto sa main prozora
        add = robot.lookup("#btnAddProduct").queryAs(Button.class);
        robot.clickOn(add);
        Stage stage = (Stage) add.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void addBtnClick(FxRobot robot) {
        //add otvara formu za unos proizvoda
        Button add = robot.lookup("#btnAddProduct").queryAs(Button.class);
        robot.clickOn(add);

        TextField id = robot.lookup("#fldID").queryAs(TextField.class);
        robot.clickOn(id);
        robot.write("nsdkcfsmld");
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        robot.clickOn(name);
        robot.write("Medicine");
        TextField price = robot.lookup("#fldPrice").queryAs(TextField.class);
        robot.clickOn(price);
        robot.write("dncksdcvsd");
        TextField quantity = robot.lookup("#fldQuantity").queryAs(TextField.class);
        robot.clickOn(quantity);
        robot.write("wrfpwldvn");
        Button addProduct = robot.lookup("#btnAdd").queryAs(Button.class);
        robot.clickOn(addProduct);

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Product ID has to be an integer!", dialogPane.getContentText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);

        robot.clickOn(id);
        id.setText("");
        robot.write("5");

        robot.clickOn(addProduct);

        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Product price has to be a real number!", dialogPane.getContentText());
        okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);

        robot.clickOn(price);
        price.setText("");
        robot.write("5");

        robot.clickOn(addProduct);

        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Product quantity has to be an integer!", dialogPane.getContentText());
        okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);

        robot.clickOn(quantity);
        quantity.setText("");
        robot.write("1.5");

        robot.clickOn(addProduct);

        dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Product quantity has to be an integer!", dialogPane.getContentText());
        okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);

        robot.clickOn(quantity);
        quantity.setText("");
        robot.write("1123");
        robot.clickOn(addProduct);

        robot.clickOn("Medicine");

        Stage stage = (Stage) add.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void updateBtnClick(FxRobot robot) {
        Button update = robot.lookup("#btnUpdateProduct").queryAs(Button.class);
        robot.clickOn(update);
        //dialog
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Product not selected!", dialogPane.getHeaderText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);
        //odabir proizvoda
        robot.clickOn("Analgin");
        robot.clickOn(update);
        //provjera polja
        TextField id = robot.lookup("#fldID").queryAs(TextField.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField price = robot.lookup("#fldPrice").queryAs(TextField.class);
        TextField quantity = robot.lookup("#fldQuantity").queryAs(TextField.class);
        TextField manufacturer = robot.lookup("#fldManufacturer").queryAs(TextField.class);
        TextArea ingredients = robot.lookup("#fldIngredients").queryAs(TextArea.class);
        TextArea description = robot.lookup("#fldDescription").queryAs(TextArea.class);
        TextArea notes = robot.lookup("#fldNotes").queryAs(TextArea.class);
        ComboBox<String> purpose = robot.lookup("#choicePurpose").queryAs(ComboBox.class);
        ComboBox<String> administrationMethod = robot.lookup("#choiceAdMethod").queryAs(ComboBox.class);
        ComboBox<String> type = robot.lookup("#choiceType").queryAs(ComboBox.class);

        assertEquals("Analgin", name.getText());
        assertEquals("3", id.getText());
        assertEquals("148", quantity.getText());
        assertEquals("2.0", price.getText());
        assertEquals("Bosnalijek d.d.", manufacturer.getText());
        assertEquals("", ingredients.getText());
        assertEquals("", description.getText());
        assertEquals("", notes.getText());
        assertEquals("Analgetik", purpose.getValue());
        assertEquals(null, administrationMethod.getValue());
        assertEquals(null, type.getValue());

        Stage stage = (Stage) update.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void addToCartBtnClick(FxRobot robot) {
        Button addToCart = robot.lookup("#addToCartBtn").queryAs(Button.class);
        robot.clickOn(addToCart);
        //dialog
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("No product is selected!", dialogPane.getHeaderText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("Analgin");
        robot.clickOn(addToCart);

        Label id = robot.lookup("#idFld").queryAs(Label.class);
        Label name = robot.lookup("#nameFld").queryAs(Label.class);
        Label total = robot.lookup("#totalFld").queryAs(Label.class);
        TextField quantity = robot.lookup("#quantityFld").queryAs(TextField.class);
        Label manufacturer = robot.lookup("#manufacturerFld").queryAs(Label.class);
        Label ingredients = robot.lookup("#ingredientsFld").queryAs(Label.class);
        Label description = robot.lookup("#descriptionFld").queryAs(Label.class);
        Label notes = robot.lookup("#notesFld").queryAs(Label.class);
        Label purpose = robot.lookup("#purposeFld").queryAs(Label.class);
        Label administrationMethod = robot.lookup("#administrationFld").queryAs(Label.class);
        Label type = robot.lookup("#typeFld").queryAs(Label.class);

        assertEquals("Analgin", name.getText());
        assertEquals("3", id.getText());
        assertEquals("1", quantity.getText());
        assertEquals("Bosnalijek d.d.", manufacturer.getText());
        assertEquals("", ingredients.getText());
        assertEquals("", description.getText());
        assertEquals("", notes.getText());
        assertEquals("Analgetik", purpose.getText());
        assertEquals(null, administrationMethod.getText());
        assertEquals(null, type.getText());

        Button plus = robot.lookup("#plusBtn").queryAs(Button.class);
        robot.clickOn(plus);
        robot.clickOn(plus);
        Button addToCart2 = robot.lookup("#addToCartBtn2").queryAs(Button.class);
        robot.clickOn(addToCart2);

        //provjerimo je li dodano u korpu
        robot.clickOn("Cart");
        robot.clickOn("Analgin");
        robot.clickOn("3");
        robot.clickOn("6.0");

        Stage stage = (Stage) addToCart.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }


    @Test
    void updateProductAlreadyInCart(FxRobot robot) {
        //update proizvoda koji je vec dodan u korpu
        Button addToCart = robot.lookup("#addToCartBtn").queryAs(Button.class);
        robot.clickOn("Analgin");
        robot.clickOn(addToCart);
        Button addToCart2 = robot.lookup("#addToCartBtn2").queryAs(Button.class);
        robot.clickOn(addToCart2);

        Button update = robot.lookup("#btnUpdateProduct").queryAs(Button.class);
        robot.clickOn("Analgin");

        robot.clickOn(update);
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Updating a product already added to cart is not allowed!", dialogPane.getHeaderText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);
        Stage stage = (Stage) update.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void discardCartClick(FxRobot robot) {
        //praznjenje korpe
        Button addToCart = robot.lookup("#addToCartBtn").queryAs(Button.class);
        robot.clickOn("Analgin");
        robot.clickOn(addToCart);
        Button addToCart2 = robot.lookup("#addToCartBtn2").queryAs(Button.class);
        robot.clickOn(addToCart2);

        robot.clickOn("Cart");
        Button discard = robot.lookup("#btnDiscardCart").queryAs(Button.class);
        TableView tableCart = robot.lookup("#tableCart").queryTableView();
        robot.clickOn(discard);
        assertTrue(tableCart.getItems().isEmpty());

        Stage stage = (Stage) tableCart.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void finalizePurchase(FxRobot robot) {
        //naplata
        Button addToCart = robot.lookup("#addToCartBtn").queryAs(Button.class);
        robot.clickOn("Analgin");
        robot.clickOn(addToCart);
        Button addToCart2 = robot.lookup("#addToCartBtn2").queryAs(Button.class);
        robot.clickOn(addToCart2);

        robot.clickOn("Cart");
        Button finish = robot.lookup("#btnFinalize").queryAs(Button.class);
        robot.clickOn(finish);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ALT).press(KeyCode.F4).release(KeyCode.F4).
                release(KeyCode.ALT);

        TableView tableCart = robot.lookup("#tableCart").queryTableView();
        assertTrue(tableCart.getItems().isEmpty());

        Stage stage = (Stage) tableCart.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void finalizeEmptyPurchase(FxRobot robot) {
        robot.clickOn("Cart");
        Button finish = robot.lookup("#btnFinalize").queryAs(Button.class);
        robot.clickOn(finish);
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Cart empty!", dialogPane.getHeaderText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);
        Stage stage = (Stage) finish.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void removeBtnClick(FxRobot robot) {
        Button remove = robot.lookup("#btnRemoveProduct").queryAs(Button.class);
        robot.clickOn(remove);

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("No product is selected!", dialogPane.getHeaderText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);

        robot.clickOn("Analgin");
        robot.clickOn(remove);

        TableView productList = robot.lookup("#productList").queryTableView();
        assertEquals(2, productList.getItems().size());

        Stage stage = (Stage) remove.getScene().getWindow();
        Platform.runLater(() -> stage.close());

    }

    @Test
    void helpMenuClick(FxRobot robot) {
        TableView productList = robot.lookup("#productList").queryTableView();
        robot.clickOn("Help");
        robot.clickOn("Help");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("Get started");
        robot.clickOn("Creating reports");
        robot.clickOn("Adding new products");
        robot.clickOn("Updating products");
        robot.clickOn("Removing products");
        robot.clickOn("Adding to cart");
        Stage stage = (Stage) productList.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

   /* @Test
    void exitMenuClick(FxRobot robot) {
        robot.clickOn("File");
        robot.clickOn("Exit");
    }*/

    @Test
    void mniCreateAccountClick() {
    }

    @Test
    void mniLogoutClick(FxRobot robot) {
        robot.clickOn("Profile");
        robot.clickOn("Log out");
        robot.clickOn("Log out");
        Button login = robot.lookup("#loginBtn").queryAs(Button.class);
        assertTrue(login.isVisible());
        Stage stage = (Stage) login.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

/*
    @Test
    void mniDailyReportClick(FxRobot robot) {
        robot.clickOn("File");
        robot.clickOn("Create report");
        ComboBox languageChoice = robot.lookup("#reportTypeCombo").queryAs(ComboBox.class);
        robot.lookup("#reportTypeCombo").tryQuery().isPresent();
        robot.clickOn("#reportTypeCombo");
        robot.clickOn("Daily report");

        DatePicker datePicker = robot.lookup("#datePicker").queryAs(DatePicker.class);
        assertEquals(String.format(LocalDate.now().toString(), "dd. mm. yyyy"),datePicker.getValue().toString());
        robot.clickOn("Show report");
    }

    @Test
    void mniGeneralReportClick(FxRobot robot) {
        robot.clickOn("File");
        robot.clickOn("Create report");
        ComboBox languageChoice = robot.lookup("#reportTypeCombo").queryAs(ComboBox.class);
        robot.lookup("#reportTypeCombo").tryQuery().isPresent();
        robot.clickOn("#reportTypeCombo");
        robot.clickOn("General report");

        DatePicker datePicker = robot.lookup("#datePicker").queryAs(DatePicker.class);
        assertTrue(!datePicker.isVisible());
        robot.clickOn("Show report");
    }

    @Test
    void mniStocksReportClick(FxRobot robot) {
        robot.clickOn("File");
        robot.clickOn("Create report");
        ComboBox languageChoice = robot.lookup("#reportTypeCombo").queryAs(ComboBox.class);
        robot.lookup("#reportTypeCombo").tryQuery().isPresent();
        robot.clickOn("#reportTypeCombo");
        robot.clickOn("Products in stock");

        DatePicker datePicker = robot.lookup("#datePicker").queryAs(DatePicker.class);
        assertTrue(!datePicker.isVisible());
        robot.clickOn("Show report");
    }
*/

   /* @Test
    void writeToFileClick(FxRobot robot) {
        robot.clickOn("File");
        robot.clickOn("Write data to file");
    }*/

    @Test
    void searchByName(FxRobot robot) {
        ChoiceBox choiceSearch = robot.lookup("#choiceSearch").queryAs(ChoiceBox.class);
        robot.lookup("#choiceSearch").tryQuery().isPresent();
        robot.clickOn("#choiceSearch");
        robot.clickOn("By name");
        TableView products = robot.lookup("#productList").queryTableView();
        assertEquals(3, products.getItems().size());
        TextField searchBar = robot.lookup("#searchBar").queryAs(TextField.class);
        robot.clickOn(searchBar);
        robot.write("Analgin");
        assertEquals(1, products.getItems().size());
        Stage stage = (Stage) searchBar.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void searchByID(FxRobot robot) {
        ChoiceBox choiceSearch = robot.lookup("#choiceSearch").queryAs(ChoiceBox.class);
        robot.lookup("#choiceSearch").tryQuery().isPresent();
        robot.clickOn("#choiceSearch");
        robot.clickOn("By ID");
        TableView products = robot.lookup("#productList").queryTableView();
        assertEquals(3, products.getItems().size());
        TextField searchBar = robot.lookup("#searchBar").queryAs(TextField.class);
        robot.clickOn(searchBar);
        robot.write("1");
        assertEquals(1, products.getItems().size());
        Stage stage = (Stage) searchBar.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    void searchByPurpose(FxRobot robot) {
        ChoiceBox choiceSearch = robot.lookup("#choiceSearch").queryAs(ChoiceBox.class);
        robot.lookup("#choiceSearch").tryQuery().isPresent();
        robot.clickOn("#choiceSearch");
        robot.clickOn("By purpose");
        TableView products = robot.lookup("#productList").queryTableView();
        assertEquals(3, products.getItems().size());
        TextField searchBar = robot.lookup("#searchBar").queryAs(TextField.class);
        robot.clickOn(searchBar);
        robot.write("Analgetik");
        assertEquals(3, products.getItems().size());
        robot.write("djajmsolsmcs");
        assertEquals(0, products.getItems().size());
        Stage stage = (Stage) searchBar.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
}