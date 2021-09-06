package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum AdministrationMethodEN {
    LOKALNO("Local"),
    PERORALNO("Peroral"),
    SUBLINGVALNO("Sublingual"),
    REKTALNO("Rectal"),
    INTRAKUTANO("Intracutaneous"),
    SUPKUTANO("Subcutaneous"),
    INTRAMUSKULARNO("Intramuscular"),
    INTRAARTERIJALNO("Intraarterial"),
    INTRAVENOZNO("Intravenous");

    public final String label;

    private AdministrationMethodEN(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (AdministrationMethodEN a : AdministrationMethodEN.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
