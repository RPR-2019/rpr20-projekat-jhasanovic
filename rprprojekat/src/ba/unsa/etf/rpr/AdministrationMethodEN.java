package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AdministrationMethodEN {
    LOCAL("Local"),
    PERORAL("Peroral"),
    SUBLINGUAL("Sublingual"),
    RECTAL("Rectal"),
    INTRACUTANEOUS("Intracutaneous"),
    SUBCUTANEOUS("Subcutaneous"),
    INTRAMUSCULAR("Intramuscular"),
    INTRAARTERIAL("Intraarterial"),
    INTRAVENOUS("Intravenous");

    public final String label;

    AdministrationMethodEN(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (AdministrationMethodEN a : AdministrationMethodEN.values())
            list.add(a.toString());
        return list.sorted();
    }

    @Override
    public String toString() {
        return this.label;
    }
}
