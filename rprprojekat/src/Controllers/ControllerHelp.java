package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sample.Language;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerHelp {
    @FXML
    public TreeView<String> treeViewHelp;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox connectionPage;
    TreeItem<String> root;

    TreeItem<String> nodeA;
    TreeItem<String> nodeB;
    TreeItem<String> nodeC;
    TreeItem<String> nodeA2;
    TreeItem<String> nodeB2;
    TreeItem<String> nodeC2;

    private Language l= Language.getInstance();

    Image icon=new Image(getClass().getResourceAsStream("/img/folder.png"));
    private List<String> bs = new ArrayList<>();
    private List<String> en = new ArrayList<>();

    @FXML
    public void initialize(){
        bs.addAll(Arrays.asList("Početna","Kreiranje izvještaja","Dodavanje novog proizvoda",
                "Ažuriranje proizvoda","Brisanje proizvoda","Dodavanje proizvoda u korpu","Naplata"));
        en.addAll(Arrays.asList("Get started","Creating reports","Adding new products",
                "Updating products","Removing products","Adding to cart","Finalizing the purchase"));

        if(l.getLang().equals("bs")) values(bs);
        else if(l.getLang().equals("en"))values(en);

        treeViewHelp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                handle(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void values(List<String> v) {

        root=new TreeItem<>(v.get(0),new ImageView(icon));

        nodeA=new TreeItem<>(v.get(2),new ImageView(icon));
        nodeB=new TreeItem<>(v.get(3),new ImageView(icon));
        nodeC=new TreeItem<>(v.get(4),new ImageView(icon));
        nodeA2=new TreeItem<>(v.get(5),new ImageView(icon));
        nodeB2=new TreeItem<>(v.get(6),new ImageView(icon));
        nodeC2=new TreeItem<>(v.get(1),new ImageView(icon));

        root.getChildren().addAll(nodeA,nodeB,nodeC,nodeA2,nodeB2,nodeC2);
        root.setExpanded(true);

        treeViewHelp.setRoot(root);
    }


    private void handle(TreeItem<String> newValue) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        if(newValue.getValue().equals(root.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuHome.fxml"), bundle);
        else if(newValue.getValue().equals(nodeA.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuDodaj.fxml"), bundle);
        else if(newValue.getValue().equals(nodeB.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuAzuriraj.fxml"),bundle);
        else if(newValue.getValue().equals(nodeC.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuObrisi.fxml"),bundle);
        else if(newValue.getValue().equals(nodeA2.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuKorpa.fxml"),bundle);
        else if(newValue.getValue().equals(nodeB2.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuNaplata.fxml"),bundle);
        else if(newValue.getValue().equals(nodeC2.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpMenuIzvjestaj.fxml"),bundle);

        borderPane.setCenter(connectionPage);

    }

}
