package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import net.sf.jasperreports.engine.JRException;
import sample.Language;
import sample.PrintReport;
import sample.SqliteHelper;

import javax.swing.text.DateFormatter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class ControllerReportChoice {
    @FXML
    public ComboBox reportTypeCombo;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Button btnOpenReport;

    private Language l;

    @FXML
    public void initialize(){
        l=Language.getInstance();
        ObservableList<String> optionsBS = FXCollections.observableArrayList("Generalni izvještaj", "Dnevni izvještaj");
        ObservableList<String> optionsEN = FXCollections.observableArrayList("General report", "Daily report");
        if(l.getLang().equals("bs")) reportTypeCombo.setItems(optionsBS);
        else if(l.getLang().equals("en")) reportTypeCombo.setItems(optionsEN);
        reportTypeCombo.getSelectionModel().select(0);
        datePicker.setValue(LocalDate.parse(LocalDate.now().toString()));
    }

    public void btnOpenReportClick(ActionEvent actionEvent) {
        try {
            if(reportTypeCombo.getValue().toString().equals("Generalni izvještaj") || reportTypeCombo.getValue().toString().equals("General report"))
            new PrintReport().showReport(SqliteHelper.getConn(),reportTypeCombo.getSelectionModel().getSelectedItem().toString(),new HashMap<String,Object>());
            else if(reportTypeCombo.getValue().toString().equals("Dnevni izvještaj") || reportTypeCombo.getValue().toString().equals("Daily report")) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("datum",datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                new PrintReport().showReport(SqliteHelper.getConn(), reportTypeCombo.getSelectionModel().getSelectedItem().toString(),map);
            }
        } catch (JRException | SQLException e1) {
            e1.printStackTrace();
        }
    }
}
