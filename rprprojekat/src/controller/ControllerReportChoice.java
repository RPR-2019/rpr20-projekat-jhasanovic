package controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JRException;
import sample.Language;
import sample.PrintReport;
import sample.SqliteHelper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class ControllerReportChoice {
    @FXML
    public ComboBox<String> reportTypeCombo;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Button btnOpenReport;


    @FXML
    public void initialize() {
        datePicker.setVisible(false);
        Language l = Language.getInstance();
        ObservableList<String> optionsBS = FXCollections.observableArrayList("Generalni izvještaj", "Dnevni izvještaj", "Izvještaj o stanju");
        ObservableList<String> optionsEN = FXCollections.observableArrayList("General report", "Daily report", "Products in stock");
        if (l.getLang().equals("bs")) reportTypeCombo.setItems(optionsBS);
        else if (l.getLang().equals("en")) reportTypeCombo.setItems(optionsEN);
        reportTypeCombo.getSelectionModel().select(0);
        datePicker.setValue(LocalDate.parse(LocalDate.now().toString()));

        reportTypeCombo.valueProperty().addListener((ov, t, t1) ->
                datePicker.setVisible(t1.equals("Dnevni izvještaj") || t1.equals("Daily report")));
    }

    public void btnOpenReportClick() {
        try {
            if (reportTypeCombo.getValue().equals("Generalni izvještaj") || reportTypeCombo.getValue().equals("General report"))
                new PrintReport().showReport(SqliteHelper.getConn(), reportTypeCombo.getSelectionModel().getSelectedItem(), new HashMap<>());
            else if (reportTypeCombo.getValue().equals("Dnevni izvještaj") || reportTypeCombo.getValue().equals("Daily report")) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("datum", datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                new PrintReport().showReport(SqliteHelper.getConn(), reportTypeCombo.getSelectionModel().getSelectedItem(), map);
            } else if (reportTypeCombo.getValue().equals("Izvještaj o stanju") || reportTypeCombo.getValue().equals("Products in stock"))
                new PrintReport().showReport(SqliteHelper.getConn(), reportTypeCombo.getSelectionModel().getSelectedItem(), new HashMap<>());
        } catch (JRException | SQLException e1) {
            e1.printStackTrace();
        }
    }
}
