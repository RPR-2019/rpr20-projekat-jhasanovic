package controller;

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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.Language;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class ControllerLanguage implements Initializable {

    ObservableList<String> options = FXCollections.observableArrayList("Bosanski", "English");
    @FXML
    public Button startBtn;
    @FXML
    public ComboBox<String> languageChoice = new ComboBox<>(options);

    Language l = Language.getInstance();

    public void btnClick(ActionEvent actionEvent) throws IOException {

        Stage myStage = new Stage();
        myStage.setResizable(false);
        if (languageChoice.getSelectionModel().getSelectedItem().equals("Bosanski")) {
            Locale.setDefault(new Locale("bs", "BA"));
            l.setLang("bs");
        } else if (languageChoice.getSelectionModel().getSelectedItem().equals("English")) {
            Locale.setDefault(new Locale("en", "UK"));
            l.setLang("en");
        }

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        if(l.getLang().equals("bs")) myStage.setTitle("Prijava");
        else if(l.getLang().equals("en")) myStage.setTitle("Login");

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
