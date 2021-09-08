package dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.CartProduct;
import sample.SqliteHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CartDAO {
    private static CartDAO instance = null;
    private final Connection conn;
    private PreparedStatement allProductsQuery;
    private final PreparedStatement removeProductQuery;
    private final PreparedStatement addToCartQuery;
    private final PreparedStatement updateCartQuery;
    private final PreparedStatement getQuantityQuery;
    private final PreparedStatement discardCartQuery;
    private final PreparedStatement getTotalQuery;
    private final PreparedStatement sameProductInCart;
    private final PreparedStatement getPriceQuery;
    private final PreparedStatement getCartSizeQuery;

    private CartDAO() throws SQLException {
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            allProductsQuery = conn.prepareStatement("SELECT * FROM korpa");
        } catch (SQLException e) {
            createDatabase();
            allProductsQuery = conn.prepareStatement("SELECT * FROM korpa");
        }
        removeProductQuery = conn.prepareStatement("DELETE FROM korpa WHERE id=?");
        addToCartQuery = conn.prepareStatement("INSERT INTO korpa VALUES(?,?,?,?)");
        updateCartQuery = conn.prepareStatement("UPDATE korpa SET quantity=?,price=? WHERE id=?");
        getQuantityQuery = conn.prepareStatement("SELECT quantity FROM korpa WHERE id=?");
        discardCartQuery = conn.prepareStatement("DELETE FROM korpa");
        getTotalQuery = conn.prepareStatement("SELECT SUM(price) FROM korpa");
        sameProductInCart = conn.prepareStatement("SELECT COUNT(*) FROM korpa WHERE id=?");
        getPriceQuery = conn.prepareStatement("SELECT price FROM korpa WHERE id=?");
        getCartSizeQuery = conn.prepareStatement("SELECT COUNT(*) FROM korpa");
    }

    private void createDatabase() {
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("apoteka.db.sql"));
            String sqlQuery = "";
            while (input.hasNext()) {
                sqlQuery += input.nextLine();
                if (sqlQuery.length() > 1 && sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            input.close();
        }catch(FileNotFoundException e){
            System.out.println("Ne postoji SQL datoteka, nastavljam sa praznom bazom");
        }
    }

    public static CartDAO getInstance() throws SQLException {
        if(instance==null) instance=new CartDAO();
        return instance;
    }

    public ObservableList<CartProduct> getProducts(){
        ObservableList<CartProduct> result = FXCollections.observableArrayList();
        try {
            ResultSet rs = allProductsQuery.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer quantity=rs.getInt(3);
                Double price=rs.getDouble(4);
                CartProduct p = new CartProduct(id,name,quantity,price);
                result.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    public ArrayList<CartProduct> getProductsArrayList(){
        ArrayList<CartProduct> result = new ArrayList<>();
        try {
            ResultSet rs = allProductsQuery.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer quantity=rs.getInt(3);
                Double price=rs.getDouble(4);
                CartProduct p = new CartProduct(id,name,quantity,price);
                result.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    public void addProductToCart(CartProduct p){
        try {

            addToCartQuery.setInt(1, p.getId());
            addToCartQuery.setString(2, p.getName());
            addToCartQuery.setInt(3, p.getQuantity());
            addToCartQuery.setDouble(4, p.getPrice());

            addToCartQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void updateCart(Integer id,Integer quantity,Double price){
        try {
            updateCartQuery.setInt(1, quantity);
            updateCartQuery.setDouble(2, price);
            updateCartQuery.setInt(3, id);

            updateCartQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja korpe\nIzuzetak: " + sqlException.getMessage());
        }
    }


    public int getQuantity(Integer id){//vraca kolicinu proizvoda u korpi
        int quantity=0;
        try {
            getQuantityQuery.setInt(1, id);

           ResultSet rs = getQuantityQuery.executeQuery();
           quantity = rs.getInt(1);

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dohvaćanja količine\nIzuzetak: " + sqlException.getMessage());
        }
        return quantity;
    }

    public double getTotal(){//vraca ukupnu cijenu korpe
        double total=0;
        try {
            ResultSet rs = getTotalQuery.executeQuery();
            total = rs.getInt(1);

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dohvaćanja totala\nIzuzetak: " + sqlException.getMessage());
        }
        return total;
    }

    public void emptyOut() {
        try {
            discardCartQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja podataka iz korpe\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void removeProduct(CartProduct p) {
        try {
            removeProductQuery.setInt(1, p.getId());
            removeProductQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public int getProductCount(Integer id){
        int count=0;
        try {
            sameProductInCart.setInt(1,id);
            ResultSet rs = sameProductInCart.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u count upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }


    public double getPrice(Integer id) {
        double count=0;
        try {
            getPriceQuery.setInt(1,id);
            ResultSet rs = getPriceQuery.executeQuery();
            count = rs.getDouble(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u getPrice upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }

    public int getCartSize() {
        int count=0;
        try {
            ResultSet rs = getCartSizeQuery.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u getRowCount upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }
}
