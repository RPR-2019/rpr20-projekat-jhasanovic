package Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Language;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ControllerLogin {
    private Language l = Language.getInstance();
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
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/pregledProizvoda.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        if(l.getLang().equals("bs")) myStage.setTitle("Apoteka");
        else if(l.getLang().equals("en")) myStage.setTitle("Pharmacy");
        myStage.show();
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
