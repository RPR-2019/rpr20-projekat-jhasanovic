package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public enum SearchCriteriaBS {
    BYNAME("Po nazivu"),
    BYID("Po šifri"),
    BYPURPOSE("Po namjeni");


    public final String label;

    SearchCriteriaBS(String label) {
        this.label = label;
    }

    public static ObservableList<String> getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        for (SearchCriteriaBS a : SearchCriteriaBS.values())
            list.add(a.toString());
        return list;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
