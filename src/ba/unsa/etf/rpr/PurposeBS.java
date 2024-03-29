package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum PurposeBS {
    ANTIPYRETIC("Antipiretik"),
    ANALGESIC("Analgetik"),
    ANTIBIOTIC("Antibiotik"),
    ANTIDEPRESSANT("Antidepresant"),
    ANTICOAGULANT("Antikoagulant"),
    ANTISEPTIC("Antiseptik"),
    SEDATIVE("Sedativ"),
    ANTIHISTAMINE("Antihistaminik");


    public final String label;

    PurposeBS(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (PurposeBS a : PurposeBS.values())
            list.add(a.toString());
        return list.sorted();
    }

    @Override
    public String toString() {
        return this.label;
    }

}
