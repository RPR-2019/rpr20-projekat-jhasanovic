package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum PurposeBS {
    ANTIPIRETIK("Antipiretik"),
    ANALGETIK("Analgetik"),
    ANTIBIOTIK("Antibiotik"),
    ANTIDEPRESANT("Antidepresant"),
    ANTIKOAGULANT("Antikoagulant"),
    ANTISEPTIK("Antiseptik"),
    SEDATIV("Sedativ");


    public final String label;

    private PurposeBS(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (PurposeBS a : PurposeBS.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }

}
