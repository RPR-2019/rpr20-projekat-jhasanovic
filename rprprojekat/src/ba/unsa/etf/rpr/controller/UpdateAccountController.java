package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.CurrentUser;
import ba.unsa.etf.rpr.Language;
import ba.unsa.etf.rpr.beans.User;
import ba.unsa.etf.rpr.dal.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

public class UpdateAccountController {
    @FXML
    public TextField emailTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Button updateBtn;

    private UserDAO daoUser;
    private CurrentUser user;
    private Language l;

    @FXML
    public void initialize() throws SQLException {
        setFieldColor(passwordTextField);
        daoUser = UserDAO.getInstance();
        user = CurrentUser.getInstance();
        l = Language.getInstance();
    }

    public boolean validateEmail(String email) {
        String uri = "https://zerobounce1.p.rapidapi.com/v2/validate?email=";
        String mail = email;
        mail.replace("@", "%40");
        uri += mail;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri + "&ip_address=%20"))
                .header("x-rapidapi-host", "zerobounce1.p.rapidapi.com")
                .header("x-rapidapi-key", "d669e375d6mshaff3ddd3467243ap1a5d2djsne33cf02cead8")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(response.body());
        return jsonObject.getString("status").equals("valid");
    }

    public void updateBtnClick(ActionEvent actionEvent) {

        if (!emailTextField.getText().trim().isEmpty() && !validateEmail(emailTextField.getText())) {//dozvoliti da se mail ne unese
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Greška pri ažuriranju računa!");
                alert.setContentText("Email nije ispravan ili ne postoji!");
            } else if (l.getLang().equals("en")) {
                alert.setTitle("Error");
                alert.setHeaderText("Error updating account!");
                alert.setContentText("Invalid or nonexistent email!");
            }
            alert.showAndWait();
        } else if (passwordTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (l.getLang().equals("bs")) {
                alert.setTitle("Greška");
                alert.setHeaderText("Greška pri ažuriranju računa!");
                alert.setContentText("Polje lozinka ne smije biti prazno!");
            } else if (l.getLang().equals("en")) {
                alert.setTitle("Error");
                alert.setHeaderText("Error updating account!");
                alert.setContentText("Field password cannot be empty!");
            }
            alert.showAndWait();
        } else if ((!emailTextField.getText().trim().isEmpty() && validateEmail(emailTextField.getText())) || !passwordTextField.getText().trim().isEmpty()) {
            daoUser.updateUser(new User(user.getUsername(), passwordTextField.getText(), emailTextField.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            if (l.getLang().equals("bs")) alert.setContentText("Uspješno ste ažurirali račun!");
            else if (l.getLang().equals("en")) alert.setContentText("You have successfully updated your account!");
            alert.showAndWait();

            Stage stage = (Stage) emailTextField.getScene().getWindow();
            stage.close();
        }
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
}
