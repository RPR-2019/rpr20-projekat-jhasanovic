package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum MedicationTypeEN {
    INJECTION("Injection"),
    CAPSULES("Capsules"),
    CREAM("Cream"),
    OITNMENT("Ointment"),
    SOLUTION("Solution"),
    SYRUP("Syrup"),
    PILLS("Pills");

    public final String label;

    private MedicationTypeEN(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (MedicationTypeEN a : MedicationTypeEN.values())
            list.add(a.toString());
        return list.sorted();
    }

    @Override
    public String toString() {
        return this.label;
    }
}
