package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum PurposeEN {
    ANTIPIRETIK("Antipyretic"),
    ANALGETIK("Analgesic"),
    ANTIBIOTIK("Antibiotic"),
    ANTIDEPRESANT("Antidepressant"),
    ANTIKOAGULANT("Anticoagulant"),
    ANTISEPTIK("Antiseptic"),
    SEDATIV("Sedative");

    public final String label;

    private PurposeEN(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (PurposeEN a : PurposeEN.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }

}
