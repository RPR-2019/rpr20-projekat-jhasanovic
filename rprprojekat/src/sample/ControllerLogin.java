package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControllerLogin {
    @FXML
    public Button loginBtn;
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public void initialize(){
        usernameTextField.getStyleClass().add("emptyField");
        usernameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (usernameTextField.getText().trim().isEmpty()) {
                    usernameTextField.getStyleClass().removeAll("nonEmptyField");
                    usernameTextField.getStyleClass().add("emptyField");
                } else {
                    usernameTextField.getStyleClass().removeAll("emptyField");
                    usernameTextField.getStyleClass().add("nonEmptyField");
                }
            }
        });

    }

    public void loginClick(ActionEvent actionEvent) {
        //provjeriti da li se username i odgovarajuci password nalaze u bazi
        //admina hardkodirati na pocetku

        //ako username ili password nisu ispravni
        /*Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText("");
        alert.setContentText("Pogrešan username ili password!");
        alert.showAndWait();*/
    }
}
