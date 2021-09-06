package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum MedicationTypeBS {
    INJEKCIJA("Injekcija"),
    KAPSULE("Kapsule"),
    KREMA("Krema"),
    MAST("Mast"),
    OTOPINA("Otopina"),
    SIRUP("Sirup"),
    TABLETE("Tablete");

    public final String label;

    private MedicationTypeBS(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (MedicationTypeBS a : MedicationTypeBS.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
