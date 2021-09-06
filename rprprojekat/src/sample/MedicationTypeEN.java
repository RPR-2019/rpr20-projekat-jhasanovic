package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum MedicationTypeEN {
    INJEKCIJA("Injection"),
    KAPSULE("Capsules"),
    KREMA("Cream"),
    MAST("Ointment"),
    OTOPINA("Solution"),
    SIRUP("Syrup"),
    TABLETE("Pills");

    public final String label;

    private MedicationTypeEN(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (MedicationTypeEN a : MedicationTypeEN.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
