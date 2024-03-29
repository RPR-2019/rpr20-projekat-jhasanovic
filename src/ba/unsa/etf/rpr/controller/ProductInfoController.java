package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.beans.CartProduct;
import ba.unsa.etf.rpr.dal.CartDAO;
import ba.unsa.etf.rpr.dal.ProductDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductInfoController implements Initializable {

    @FXML
    public Label nameFld;
    @FXML
    public Label idFld;
    @FXML
    public Label typeFld;
    @FXML
    public Button minusBtn;
    @FXML
    public TextField quantityFld;
    @FXML
    public Button plusBtn;
    @FXML
    public Label totalFld;
    @FXML
    public Button addToCartBtn2;
    @FXML
    public Button cancelBtn;
    @FXML
    public Label manufacturerFld;
    @FXML
    public Label administrationFld;
    @FXML
    public Label descriptionFld;
    @FXML
    public Label ingredientsFld;
    @FXML
    public Label purposeFld;
    @FXML
    public Label notesFld;

    private Integer maxQt;
    private Double price;
    private CartDAO daoCart;
    private ProductDAO dao;

    public void maxQuantity(int max){
        maxQt = max;
    }

    public void getPrice(Double p){
        price=p;
    }


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){

        quantityFld.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.equals("") && Integer.parseInt(newValue) > maxQt)
                    quantityFld.setText(Integer.toString(maxQt));
                else if (!newValue.equals("") && Integer.parseInt(newValue) < 1) quantityFld.setText("1");
                else if (!newValue.equals("")){
                    totalFld.setText((String.format("%.2f",Integer.parseInt(quantityFld.getText()) * price)) + " KM");
                }

                else totalFld.setText("0 KM");
            }
            catch(NumberFormatException e){ //nije dopusten unos bilo cega sto nije broj u textfield za kolicinu
                quantityFld.setText("1");
            }

        });

        try {
            daoCart = CartDAO.getInstance();
            dao = ProductDAO.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addToCartBtnClick(ActionEvent actionEvent) {
        String price2 = totalFld.getText().replace(",", ".").substring(0, 5);
        if(daoCart.getProductCount(Integer.parseInt(idFld.getText()))>0){
            //azurirati postojeci proizvod u korpi
            daoCart.updateCart(Integer.parseInt(idFld.getText()), Integer.parseInt(quantityFld.getText()) + daoCart.getQuantity(Integer.parseInt(idFld.getText())), daoCart.getPrice(Integer.parseInt(idFld.getText())) + Double.parseDouble(price2));
        }
        else {
            CartProduct p = new CartProduct(Integer.parseInt(idFld.getText()), nameFld.getText(), Integer.parseInt(quantityFld.getText()), Double.parseDouble(price2));
            daoCart.addProductToCart(p);
        }
        //smanjiti dostupne kolicine
        dao.changeQuantity(Integer.parseInt(idFld.getText()),dao.getQuantity(Integer.parseInt(idFld.getText()))-Integer.parseInt(quantityFld.getText()));
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

    }

    public void minusBtnClick() {
        if (!quantityFld.getText().equals("")) {
            int qt = Integer.parseInt(quantityFld.getText());
            if (qt > 1) {
                qt = qt - 1;
                quantityFld.setText(Integer.toString(qt));
            }
        } else if (quantityFld.getText().equals(""))
            quantityFld.setText("1");
    }

    public void plusBtnClick() {
        if (!quantityFld.getText().equals("")) {
            int qt = Integer.parseInt(quantityFld.getText());
            if (qt <= maxQt) {
                qt = qt + 1;
                quantityFld.setText(Integer.toString(qt));
            }
        } else if (quantityFld.getText().equals(""))
            quantityFld.setText("1");
    }

    public void cancelBtnClick(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
