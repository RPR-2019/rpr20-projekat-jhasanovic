package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ControllerLanguage implements Initializable {

    ObservableList<String> options = FXCollections.observableArrayList("Bosanski", "English");
    @FXML
    public Button startBtn;
    @FXML
    public ChoiceBox<String> languageChoice=new ChoiceBox<>(options);

    public void btnClick(ActionEvent actionEvent) throws Exception{
        //postaviti odabrani jezik

        //otvoriti login prozor
        Stage myStage = new Stage();
        Locale.setDefault(new Locale("bs", "BA"));
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"),bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        myStage.show();
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageChoice.setItems(options);
        languageChoice.getSelectionModel().select(0);
    }
}
