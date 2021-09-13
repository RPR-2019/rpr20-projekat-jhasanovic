package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class LanguageControllerTest {
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("/fxml/language.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void enterBtnClickEnglish(FxRobot robot) throws IOException {
        ComboBox languageChoice = robot.lookup("#languageChoice").queryAs(ComboBox.class);
        robot.lookup("#languageChoice").tryQuery().isPresent();

        robot.clickOn("#languageChoice");
        robot.clickOn("English");
        Button startBtn = robot.lookup("#startBtn").queryAs(Button.class);
        robot.clickOn("#startBtn");

        Button loginBtn = robot.lookup("#loginBtn").queryAs(Button.class);
        assertEquals("Login", loginBtn.getText());

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

    @Test
    public void enterBtnClickBosanski(FxRobot robot) throws IOException {
        ComboBox languageChoice = robot.lookup("#languageChoice").queryAs(ComboBox.class);
        robot.lookup("#languageChoice").tryQuery().isPresent();

        robot.clickOn("#languageChoice");
        robot.clickOn("Bosanski");
        Button startBtn = robot.lookup("#startBtn").queryAs(Button.class);
        robot.clickOn("#startBtn");

        Button loginBtn = robot.lookup("#loginBtn").queryAs(Button.class);
        assertEquals("Prijava", loginBtn.getText());

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }

}