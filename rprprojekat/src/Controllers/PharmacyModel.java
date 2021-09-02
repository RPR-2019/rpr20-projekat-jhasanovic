package Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Product;

import java.util.List;
import java.util.stream.Collectors;

public class PharmacyModel {
  private ObservableList<Product> products= FXCollections.observableArrayList();
  private SimpleObjectProperty<Product> trenutni=new SimpleObjectProperty<>();

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> products) {
        this.products = products;
    }

    public Product getTrenutni() {
        return trenutni.get();
    }

    public SimpleObjectProperty<Product> trenutniProperty() {
        return trenutni;
    }

    public void setTrenutni(Product trenutni) {
        this.trenutni.set(trenutni);
    }

    public void napuni(){
        products.add(new Product("Paracetamol","1",2.,1,"Analgetik i antipiretik","",
                "PARACETAMOL tablete su namjenjene za oralnu primjenu.","BOSNALIJEK d.d.",
                "PARACETAMOL BOSNALIJEK je blagi analgetik i antipiretik i preporučuje se za liječenje većine bolnih i " +
                        "febrilnih stanja, naprimjer: glavobolje uključujući i migrenu i tenzione glavobolje, zubobolje, bola u leđima," +
                        " reumatskog bola i bola u mišićima, dismenoreje, grlobolje, te za olakšanje povišene tjelesne temperature i " +
                        "bolova uzrokovanih s prehladom i gripom. Također, preporučuje se za simptomatsko olakšanje bola kod blažeg " +
                        "oblika artritisa.","Jedna PARACETAMOL tableta sadrži: Paracetamola 500,00 mg. PARACETAMOL tablete" +
                " sadrže sljedeće pomoćne supstance: povidon K 30, natrij škrobni glikolat i stearinsku kiselinu.","Tablete bijele do gotovo bijele boje, okruglog oblika, " +
                "sa diobenom crtom na jednoj strani."));
        products.add(new Product("Flonidan","2",2.,11,"","","","","","","Tablete"));
        products.add(new Product("Analgin","3",2.,15,"","","","","","","Tablete"));
    }

    /*public String getMedicationType(String id){
    List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
    //trebao bi biti samo jedan proizvod u listi, jer ne moze biti vise od jednog proizvoda sa istom sifrom
        return trazeni.get(0).getMedicationType();
    }

    public String getPurpose(String id){
        List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
        return trazeni.get(0).getPurpose();
    }

    public String getNotes(String id){
        List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
        return trazeni.get(0).getNotes();
    }

    public String getAdministrationMethod(String id){
        List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
        return trazeni.get(0).getAdministrationMethod();
    }

    public String getManufacturer(String id){
        List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
        return trazeni.get(0).getManufacturer();
    }

    public String getDescription(String id){
        List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
        return trazeni.get(0).getDescription();
    }

    public String getIngredients(String id){
        List<Product> trazeni = products.stream().filter(p->p.getID().equals(id)).collect(Collectors.toList());
        return trazeni.get(0).getIngredients();
    }*/
}
