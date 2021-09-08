package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.CurrentUser;
import sample.Language;
import sample.User;
import dal.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ControllerLogin {
    private CurrentUser user;
    private Language l = Language.getInstance();
    private UserDAO daoUsers;
    @FXML
    public Button loginBtn;
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;

    @FXML
    public void initialize() throws SQLException {
        user = CurrentUser.getInstance();
        daoUsers = UserDAO.getInstance();
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
    public void loginClick(ActionEvent actionEvent) throws IOException {
        if(daoUsers.credentialsValidation(new User(usernameTextField.getText(),passwordTextField.getText()))>0){
            Stage myStage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"),bundle);
            Parent root = loader.load();

            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setMinWidth(640);
            myStage.setMinHeight(480);
            if(l.getLang().equals("bs")) myStage.setTitle("Apoteka");
            else if(l.getLang().equals("en")) myStage.setTitle("Pharmacy");
            myStage.show();
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
            user.setUsername(usernameTextField.getText());
        }
        else {
            if (l.getLang().equals("bs")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("");
                alert.setTitle("Greška");
                alert.setContentText("Pogrešan username ili password!");
                alert.showAndWait();
            } else if (l.getLang().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("");
                alert.setTitle("Error");
                alert.setContentText("Incorrect username or password!");
                alert.showAndWait();
            }
        }
    }
}
