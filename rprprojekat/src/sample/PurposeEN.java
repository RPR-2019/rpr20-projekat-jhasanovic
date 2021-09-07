package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum PurposeEN {
    ANTIPYRETIC("Antipyretic"),
    ANALGESIC("Analgesic"),
    ANTIBIOTIC("Antibiotic"),
    ANTIDEPRESSANT("Antidepressant"),
    ANTICOAGULANT("Anticoagulant"),
    ANTISEPTIC("Antiseptic"),
    SEDATIVE("Sedative"),
    ANTIHISTAMINE("Antihistamine");

    public final String label;

    private PurposeEN(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (PurposeEN a : PurposeEN.values())
            list.add(a.toString());
        return list.sorted();
    }

    @Override
    public String toString() {
        return this.label;
    }

}
