package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AdministrationMethodBS {
    LOCAL("Lokalno"),
    PERORAL("Peroralno"),
    SUBLINGUAL("Sublingvalno"),
    RECTAL("Rektalno"),
    INTRACUTANEOUS("Intrakutano"),
    SUBCUTANEOUS("Supkutano"),
    INTRAMUSCULAR("Intramuskularno"),
    INTRAARTERIAL("Intraarterijalno"),
    INTRAVENOUS("Intravenozno");

    public final String label;

    private AdministrationMethodBS(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (AdministrationMethodBS a : AdministrationMethodBS.values())
            list.add(a.toString());
        return list.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }

}
