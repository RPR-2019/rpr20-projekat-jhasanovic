package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance=null;
    private Connection conn;
    private PreparedStatement dodajKorisnikaUpit,azurirajSifruUpit,postojeciKorisnikUpit,existingUsernameUpit;
    private Language l;

    private UserDAO() throws SQLException {
        l=Language.getInstance();
        //String url = "jdbc:sqlite:" + System.getProperty("user.home") + "/.apotekaapp/apoteka.db";
        String url = "jdbc:sqlite:apoteka.db";
        conn = SqliteHelper.getConn();
        try {
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnici VALUES(?,?)");
        }
        catch(SQLException e){
            kreirajBazu();
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnici VALUES(?,?)");
        }
        azurirajSifruUpit = conn.prepareStatement("UPDATE korisnici SET password=? WHERE username=?");
        postojeciKorisnikUpit = conn.prepareStatement("SELECT COUNT(*) FROM korisnici WHERE username=? AND password=?");
        existingUsernameUpit = conn.prepareStatement("SELECT COUNT(*) FROM korisnici WHERE username=?");
    }

    private void kreirajBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("apoteka.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
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
            dodajKorisnikaUpit.setString(1, u.getUsername());
            dodajKorisnikaUpit.setString(2, u.getPassword());

            dodajKorisnikaUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom dodavanja novog korisnika\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public void updateUser(String username,String password){
        try {

            azurirajSifruUpit.setString(1, password);
            azurirajSifruUpit.setString(2, username);

           azurirajSifruUpit.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println("Greška prilikom promjene šifre korisnika\nIzuzetak: " + sqlException.getMessage());
        }
    }

    public int credentialsValidation(User u){
        int count=0;
        try {
            postojeciKorisnikUpit.setString(1,u.getUsername());
            postojeciKorisnikUpit.setString(2,u.getPassword());
            ResultSet rs = postojeciKorisnikUpit.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u credential validation upitu\nIzuzetak: " + sqlException.getMessage());
        }
        return count;
    }

    public int existingUsername(User u) {
        int count=0;
        try {
            postojeciKorisnikUpit.setString(1,u.getUsername());
            ResultSet rs = postojeciKorisnikUpit.executeQuery();
            count = rs.getInt(1);
        } catch (SQLException sqlException) {
            System.out.println("Greška u existing username upitu\nIzuzetak: " + sqlException.getMessage());
        }
       /* finally{
            if(count>0){
                if(l.getLang().equals("bs"))
                    throw new UsernameTakenException("Korisnik sa korisničkim imenom "+u.getUsername()+" već postoji!");
                else if(l.getLang().equals("en"))
                    throw new UsernameTakenException("User with username "+u.getUsername()+" already exists!");
            }
        }*/
        return count;
    }


}
