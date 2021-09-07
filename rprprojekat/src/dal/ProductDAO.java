package dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.IncorrectDataException;
import sample.Product;
import sample.SqliteHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductDAO {
    private static ProductDAO instance=null;
    private Connection conn;
    private PreparedStatement allProductsQuery, searchByIDQuery, addProductQuery, deleteProductQuery,
            updateProductQuery, updateQuantityQuery, getQuantityQuery;

    private ProductDAO() throws SQLException {
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            allProductsQuery = conn.prepareStatement("SELECT * FROM proizvod ORDER BY name");
        }
        catch(SQLException e){
            createDatabase();
            allProductsQuery = conn.prepareStatement("SELECT * FROM proizvod ORDER BY name");
        }
        searchByIDQuery = conn.prepareStatement("SELECT * FROM proizvod WHERE id=?");
        deleteProductQuery = conn.prepareStatement("DELETE FROM proizvod WHERE id=?");
        addProductQuery = conn.prepareStatement("INSERT INTO proizvod VALUES(?,?,?,?,?,?,?,?,?,?,?)");
        updateProductQuery = conn.prepareStatement("UPDATE proizvod SET name=?,id=?,price=?,quantity=?," +
                "purpose=?,notes=?,administrationMethod=?,manufacturer=?,description=?,ingredients=?,type=? WHERE id=?");
        updateQuantityQuery = conn.prepareStatement("UPDATE proizvod SET quantity=? WHERE id=?");
        getQuantityQuery = conn.prepareStatement("SELECT quantity FROM proizvod WHERE id=?");
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

    public static ProductDAO getInstance() throws SQLException {
        if(instance==null) instance=new ProductDAO();
        return instance;
    }

    public ArrayList<Product> pretraga(Integer sifra) throws IncorrectDataException {
        ArrayList<Product> result = new ArrayList<>();
        try {
            searchByIDQuery.setInt(1,sifra);
            ResultSet rs = searchByIDQuery.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                Integer id = rs.getInt(2);
                Double price=rs.getDouble(3);
                int quantity=rs.getInt(4);
                String purpose=rs.getString(5);
                String notes=rs.getString(6);
                String administrationMethod=rs.getString(7);
                String manufacturer= rs.getString(8);
                String description=rs.getString(9);
                String ingredients=rs.getString(10);
                String medicationType=rs.getString(11);
                Product p = new Product(name,id,price,quantity,purpose,notes,administrationMethod,manufacturer,description,ingredients,medicationType);
                result.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public ObservableList<Product> getProducts() throws IncorrectDataException {
        ObservableList<Product> result = FXCollections.observableArrayList();
        try {
            ResultSet rs = allProductsQuery.executeQuery();
            while(rs.next()){
                String name = rs.getString(1);
                Integer id = rs.getInt(2);
                Double price=rs.getDouble(3);
                int quantity=rs.getInt(4);
                String purpose=rs.getString(5);
                String notes=rs.getString(6);
                String administrationMethod=rs.getString(7);
                String manufacturer= rs.getString(8);
                String description=rs.getString(9);
                String ingredients=rs.getString(10);
                String medicationType=rs.getString(11);
                Product p = new Product(name,id,price,quantity,purpose,notes,administrationMethod,manufacturer,description,ingredients,medicationType);
                result.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public void addProduct(Product p){
        try {
            addProductQuery.setString(1, p.getName());
            addProductQuery.setInt(2, p.getID());
            addProductQuery.setDouble(3, p.getPrice());
            addProductQuery.setInt(4, p.getQuantity());
            addProductQuery.setString(5, p.getPurpose());
            addProductQuery.setString(6, p.getNotes());
            addProductQuery.setString(7, p.getAdministrationMethod());
            addProductQuery.setString(8, p.getManufacturer());
            addProductQuery.setString(9, p.getDescription());
            addProductQuery.setString(10, p.getIngredients());
            addProductQuery.setString(11, p.getMedicationType());

            addProductQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
    public void updateProduct(Product p,Integer index){
        try {
            updateProductQuery.setString(1, p.getName());
            updateProductQuery.setInt(2, p.getID());
            updateProductQuery.setDouble(3, p.getPrice());
            updateProductQuery.setInt(4, p.getQuantity());
            updateProductQuery.setString(5, p.getPurpose());
            updateProductQuery.setString(6, p.getNotes());
            updateProductQuery.setString(7, p.getAdministrationMethod());
            updateProductQuery.setString(8, p.getManufacturer());
            updateProductQuery.setString(9, p.getDescription());
            updateProductQuery.setString(10, p.getIngredients());
            updateProductQuery.setString(11, p.getMedicationType());
            updateProductQuery.setInt(12,index);
            updateProductQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }
    public void removeProduct(Product p) {
        try {
            deleteProductQuery.setInt(1, p.getID());
            deleteProductQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom brisanja proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void changeQuantity(Integer id,int kolicina){
        try {
            updateQuantityQuery.setInt(1, kolicina);
            updateQuantityQuery.setInt(2,id);
            updateQuantityQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom ažuriranja količine proizvoda\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public int getQuantity(Integer id){//vraca kolicinu proizvoda
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
}
