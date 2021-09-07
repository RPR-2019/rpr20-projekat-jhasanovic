package dal;

import sample.Language;
import sample.SqliteHelper;
import sample.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance=null;
    private Connection conn;
    private PreparedStatement addUserQuery, updatePasswordQuery, userAlreadyExistsQuery,existingUsernameUpit;
    private Language l;

    private UserDAO() throws SQLException {
        l=Language.getInstance();
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            addUserQuery = conn.prepareStatement("INSERT INTO korisnici VALUES(?,?)");
        }
        catch(SQLException e){
            createDatabase();
            addUserQuery = conn.prepareStatement("INSERT INTO korisnici VALUES(?,?)");
        }
        updatePasswordQuery = conn.prepareStatement("UPDATE korisnici SET password=? WHERE username=?");
        userAlreadyExistsQuery = conn.prepareStatement("SELECT COUNT(*) FROM korisnici WHERE username=? AND password=?");
        existingUsernameUpit = conn.prepareStatement("SELECT COUNT(*) FROM korisnici WHERE username=?");
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

    public static UserDAO getInstance() throws SQLException {
        if(instance==null) instance=new UserDAO();
        return instance;
    }



    public void addUser(User u){
        try {
            addUserQuery.setString(1, u.getUsername());
            addUserQuery.setString(2, u.getPassword());

            addUserQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja novog korisnika\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void updateUser(String username,String password){
        try {
            updatePasswordQuery.setString(1, password);
            updatePasswordQuery.setString(2, username);

           updatePasswordQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom promjene šifre korisnika\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public int credentialsValidation(User u){
        int count=0;
        try {
            userAlreadyExistsQuery.setString(1,u.getUsername());
            userAlreadyExistsQuery.setString(2,u.getPassword());
            ResultSet rs = userAlreadyExistsQuery.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u credential validation upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }

    public int existingUsername(User u) {
        int count=0;
        try {
            userAlreadyExistsQuery.setString(1,u.getUsername());
            ResultSet rs = userAlreadyExistsQuery.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u existing username upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }


}
