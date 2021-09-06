package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AdministrationMethodBS {
    LOKALNO("Lokalno"),
    PERORALNO("Peroralno"),
    SUBLINGVALNO("Sublingvalno"),
    REKTALNO("Rektalno"),
    INTRAKUTANO("Intrakutano"),
    SUPKUTANO("Supkutano"),
    INTRAMUSKULARNO("Intramuskularno"),
    INTRAARTERIJALNO("Intraarterijalno"),
    INTRAVENOZNO("Intravenozno");

    public final String label;

    private AdministrationMethodBS(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (AdministrationMethodBS a : AdministrationMethodBS.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }

}
