package ba.unsa.etf.rpr.controller;

import ba.unsa.etf.rpr.Language;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class HelpController {
    @FXML
    public TreeView<String> treeViewHelp;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public BorderPane borderPane;
    @FXML
    private VBox connectionPage;

    TreeItem<String> root;

    TreeItem<String> nodeA;
    TreeItem<String> nodeB;
    TreeItem<String> nodeC;
    TreeItem<String> nodeA2;
    TreeItem<String> nodeC2;

    private final Language l = Language.getInstance();

    Image icon = new Image(getClass().getResourceAsStream("/img/folder.png"));
    private final List<String> bs = new ArrayList<>();
    private final List<String> en = new ArrayList<>();

    @FXML
    public void initialize() throws IOException {
        bs.addAll(Arrays.asList("Početna", "Kreiranje izvještaja", "Dodavanje novog proizvoda",
                "Ažuriranje proizvoda", "Brisanje proizvoda", "Dodavanje proizvoda u korpu"));
        en.addAll(Arrays.asList("Get started", "Creating reports", "Adding new products",
                "Updating products", "Removing products", "Adding to cart"));

        if (l.getLang().equals("bs")) values(bs);
        else if (l.getLang().equals("en")) values(en);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpHome.fxml"), bundle);
        scrollPane.setContent(connectionPage);
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
        nodeC2=new TreeItem<>(v.get(1),new ImageView(icon));

        root.getChildren().addAll(nodeA,nodeB,nodeC,nodeA2,nodeC2);
        root.setExpanded(true);

        treeViewHelp.setRoot(root);
    }


    private void handle(TreeItem<String> newValue) throws IOException {

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");

        if(newValue.getValue().equals(root.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpHome.fxml"), bundle);
        else if(newValue.getValue().equals(nodeA.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpAddProduct.fxml"), bundle);
        else if(newValue.getValue().equals(nodeB.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpUpdate.fxml"),bundle);
        else if(newValue.getValue().equals(nodeC.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpDeleteProduct.fxml"),bundle);
        else if(newValue.getValue().equals(nodeA2.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpCart.fxml"),bundle);
        else if(newValue.getValue().equals(nodeC2.getValue()))
            connectionPage = FXMLLoader.load(getClass().getResource("/fxml/helpReports.fxml"),bundle);

        scrollPane.setContent(connectionPage);
        borderPane.setCenter(scrollPane);
    }

}
