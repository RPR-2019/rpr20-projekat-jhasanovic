package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public enum SearchCriteriaEN {
    BYNAME("By name"),
    BYID("By ID"),
    BYPURPOSE("By purpose");


    public final String label;

    SearchCriteriaEN(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (SearchCriteriaEN a : SearchCriteriaEN.values())
            list.add(a.toString());
        return list;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
