package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum MedicationTypeBS {
    INJECTION("Injekcija"),
    CAPSULES("Kapsule"),
    CREAM("Krema"),
    OINTMENT("Mast"),
    SOLUTION("Otopina"),
    SYRUP("Sirup"),
    PILLS("Tablete");

    public final String label;

    MedicationTypeBS(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (MedicationTypeBS a : MedicationTypeBS.values())
            list.add(a.toString());
        return list.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
