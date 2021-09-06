package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public enum SearchCriteriaEN {
    BYNAME("By name"),
    BYID("By ID"),
    BYPURPOSE("By purpose");


    public final String label;

    private SearchCriteriaEN(String label) {
        this.label = label;
    }
    public static ObservableList<String> getValues(){
        ObservableList<String> lista = FXCollections.observableArrayList();
        for (SearchCriteriaEN a : SearchCriteriaEN.values())
            lista.add(a.toString());
        return lista.sorted();
    }
    @Override
    public String toString() {
        return this.label;
    }
}
