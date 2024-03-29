package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.Language;
import ba.unsa.etf.rpr.beans.User;
import ba.unsa.etf.rpr.dal.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddAccountController {
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public ImageView image;

    private UserDAO daoUsers;
    private Language l;


    @FXML
    public void initialize() throws SQLException {
        daoUsers = UserDAO.getInstance();
        l = Language.getInstance();
        setFieldColor(usernameTextField);
        setFieldColor(passwordTextField);
    }

    private void setFieldColor(TextField field) {
        field.getStyleClass().add("emptyField");
        field.textProperty().addListener((observableValue, o, n) -> {
            if (field.getText().trim().isEmpty()) {
                field.getStyleClass().removeAll("nonEmptyField");
                field.getStyleClass().add("emptyField");
            } else {
                field.getStyleClass().removeAll("emptyField");
                field.getStyleClass().add("nonEmptyField");
            }
        });
    }

    public void loginClick() {
        User u = new User(usernameTextField.getText(), passwordTextField.getText(), "");
        int count = daoUsers.existingUser(u);

        if (usernameTextField.getText().trim().isEmpty() || passwordTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Greška pri kreiranju novog računa!");
                alert.setContentText("Polja ne smiju biti prazna!");
            } else if (l.getLang().equals("en")) {
                alert.setTitle("Error");
                alert.setHeaderText("Error creating new account!");
                alert.setContentText("Fields cannot be empty!");
            }
            alert.showAndWait();
        } else if (count == 0) {
            daoUsers.addUser(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (l.getLang().equals("bs")) alert.setContentText("Uspješno ste kreirali novi račun!");
            else if (l.getLang().equals("en")) alert.setContentText("You have succesfully created a new account!");
            alert.showAndWait();
            Stage stage = (Stage) passwordTextField.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Greška pri kreiranju novog računa!");
                alert.setContentText("Korisnik sa korisničkim imenom " + u.getUsername() + " već postoji!");
            } else if (l.getLang().equals("en")) {
                alert.setTitle("Error");
                alert.setHeaderText("Error creating new account!");
                alert.setContentText("User with username " + u.getUsername() + " already exists!");
            }
            alert.showAndWait();
        }
    }
}
