package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.Language;
import sample.User;
import dal.UserDAO;
import java.sql.SQLException;

public class ControllerAddAccount {
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

        usernameTextField.getStyleClass().add("emptyField");
        usernameTextField.textProperty().addListener((observableValue, o, n) -> {
            if (usernameTextField.getText().trim().isEmpty()) {
                usernameTextField.getStyleClass().removeAll("nonEmptyField");
                usernameTextField.getStyleClass().add("emptyField");
            } else {
                usernameTextField.getStyleClass().removeAll("emptyField");
                usernameTextField.getStyleClass().add("nonEmptyField");
            }
        });
        passwordTextField.getStyleClass().add("emptyField");
        passwordTextField.textProperty().addListener((observableValue, o, n) -> {
            if (passwordTextField.getText().trim().isEmpty()) {
                passwordTextField.getStyleClass().removeAll("nonEmptyField");
                passwordTextField.getStyleClass().add("emptyField");
            } else {
                passwordTextField.getStyleClass().removeAll("emptyField");
                passwordTextField.getStyleClass().add("nonEmptyField");
            }
        });
    }

    public void loginClick() {
        User u = new User(usernameTextField.getText(), passwordTextField.getText());
        int count = daoUsers.existingUser(u);
        if (count == 0) {
            daoUsers.addUser(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (l.getLang().equals("bs")) alert.setContentText("Uspješno ste kreirali novi račun!");
            else if (l.getLang().equals("en")) alert.setContentText("You have succesfully created a new account!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Greška pri kreiranju novog računa!");
                alert.setContentText("Korisnik sa korisničkim imenom "+u.getUsername()+" već postoji!");
            }
            else if(l.getLang().equals("en")){
                alert.setTitle("Error");
                alert.setHeaderText("Error creating new account!");
                alert.setContentText("User with username "+u.getUsername()+" already exists!");
            }
            alert.showAndWait();
        }
    }
}
