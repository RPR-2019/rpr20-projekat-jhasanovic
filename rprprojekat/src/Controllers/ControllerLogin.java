package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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

    public void loginClick(ActionEvent actionEvent) throws IOException {
        //provjeriti da li se username i odgovarajuci password nalaze u bazi
        //admina hardkodirati na pocetku

        //ako username ili password nisu ispravni
        /*Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText("");
        alert.setContentText("Pogrešan username ili password!");
        alert.showAndWait();*/

        //ako je login uspjesan
        Stage myStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pregledProizvoda.fxml"));
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
