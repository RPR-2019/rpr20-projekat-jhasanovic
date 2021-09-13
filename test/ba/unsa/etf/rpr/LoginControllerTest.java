package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    @Start
    public void start(Stage stage) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        Parent root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
    }

    @Test
    void loginClick(FxRobot robot) {
        TextField username = robot.lookup("#usernameTextField").queryAs(TextField.class);
        robot.clickOn(username);
        Background bg = username.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
        robot.write("root");

        bg = username.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound = true;
        assertTrue(colorFound);

        TextField password = robot.lookup("#passwordTextField").queryAs(TextField.class);
        robot.clickOn(password);
        bg = password.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);
        robot.write("1q2w3e");

        bg = password.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("9acd32"))
                colorFound = true;
        assertTrue(colorFound);

        Button loginBtn = robot.lookup("#loginBtn").queryAs(Button.class);
        robot.clickOn(loginBtn);

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Incorrect username or password!", dialogPane.getContentText());
        Button okBtn = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okBtn);
        robot.clickOn(password);
        password.setText("");
        robot.write("1q2w3e4r");
        robot.clickOn(loginBtn);

        Button addBtn = robot.lookup("#btnAddProduct").queryAs(Button.class);
        assertEquals("Add product", addBtn.getText());

        Stage stage = (Stage) addBtn.getScene().getWindow();
        Platform.runLater(() -> stage.close());
    }
}