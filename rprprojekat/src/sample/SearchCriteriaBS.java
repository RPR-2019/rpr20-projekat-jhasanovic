package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public enum SearchCriteriaBS {
    BYNAME("Po nazivu"),
    BYID("Po šifri"),
    BYPURPOSE("Po namjeni");


    public final String label;

    private SearchCriteriaBS(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (SearchCriteriaBS a : SearchCriteriaBS.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
