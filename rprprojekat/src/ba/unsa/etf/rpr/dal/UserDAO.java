package ba.unsa.etf.rpr.dal;

import ba.unsa.etf.rpr.SqliteHelper;
import ba.unsa.etf.rpr.beans.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance = null;
    private final Connection conn;
    private final PreparedStatement addUserQuery;
    private final PreparedStatement updateDataQuery;
    private final PreparedStatement userAlreadyExistsQuery;
    private final PreparedStatement existingUsernameQuery;
    private PreparedStatement allUsersQuery;

    private UserDAO() throws SQLException {
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            allUsersQuery = conn.prepareStatement("SELECT * FROM korisnici");
        } catch (SQLException e) {
            allUsersQuery = conn.prepareStatement("SELECT * FROM korisnici");
            regenerateDatabase();
        }
        addUserQuery = conn.prepareStatement("INSERT INTO korisnici VALUES(?,?,?)");
        existingUsernameQuery = conn.prepareStatement("SELECT COUNT(*) FROM korisnici WHERE username=?");
        updateDataQuery = conn.prepareStatement("UPDATE korisnici SET password=?,email=? WHERE username=?");
        userAlreadyExistsQuery = conn.prepareStatement("SELECT COUNT(*) FROM korisnici WHERE username=? AND password=?");
    }

    private void regenerateDatabase() {
        Scanner input;
        try {
            input = new Scanner(new FileInputStream("apoteka.db.sql"));
            StringBuilder sqlQuery = new StringBuilder();
            while (input.hasNext()) {
                sqlQuery.append(input.nextLine());
                if (sqlQuery.length() > 1 && sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery.toString());
                        sqlQuery = new StringBuilder();
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
            addUserQuery.setString(3, u.getEmail());
            addUserQuery.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja novog korisnika\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void updateUser(User u) {
        try {
            updateDataQuery.setString(1, u.getPassword());
            updateDataQuery.setString(2, u.getEmail());
            updateDataQuery.setString(3, u.getUsername());

            updateDataQuery.executeUpdate();

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

    public int existingUser(User u) {
        int count = 0;
        try {
            existingUsernameQuery.setString(1, u.getUsername());
            ResultSet rs = existingUsernameQuery.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u existing username upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();
        try {
            ResultSet rs = allUsersQuery.executeQuery();
            while (rs.next()) {
                String username = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                User u = new User(username, password, email);
                result.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
