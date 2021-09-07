package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum PurposeBS {
    ANTIPYRETIK("Antipiretik"),
    ANALGESIC("Analgetik"),
    ANTIBIOTIC("Antibiotik"),
    ANTIDEPRESSANT("Antidepresant"),
    ANTICOAGULANT("Antikoagulant"),
    ANTISEPTIC("Antiseptik"),
    SEDATIVE("Sedativ");


    public final String label;

    private PurposeBS(String label) {
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
