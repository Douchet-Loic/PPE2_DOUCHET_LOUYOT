package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

public class ControllerAjoutLigneColonne implements Initializable {
    @FXML private ListView lv_ligne;
    @FXML private RadioButton rb_oui;
    @FXML private RadioButton rb_non;
    @FXML private Pane panel;
    @FXML private Button bt_valider;

    private Connexion connexion;
    private ArrayList<Ligne> lignes;
    private ObservableList lesLignes = FXCollections.observableArrayList();
    String nomColonne = "";
    String nomChamp = "";
    String typeChamp = "";
    public  void initialize(URL url, ResourceBundle resourceBundle){
        lv_ligne.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // permet de faire une sélection multiple sur la list view
        rb_oui.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                panel.setVisible(true);
            }
        });  // quand "oui" sélectionné par l' utilisateur cela affiche la list view
        rb_non.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                panel.setVisible(false);
            }
        }); // quand "non" sélectionné par l' utilisateur cela cache la list view
        bt_valider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String nom = lv_ligne.getSelectionModel().getSelectedItems().toString();
                System.out.println(nom);
                URL location = getClass().getResource("generationColonne.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = null;
                try{
                    root = (Parent) fxmlLoader.load(location.openStream());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                ControllerAffichageColonne c = fxmlLoader.getController();
                Enregistrement enregistrement = new Enregistrement(nomChamp, typeChamp, nomColonne, nom);
                c.setLesEnregistrementObserves(enregistrement);
        }});
    }

    public void setConnexion(Connexion c){
        this.connexion = c;
    }
    public void setLeslignes(ArrayList<Ligne> lignes) {       // déclaration de la liste ligne
        this.lignes = lignes;
        for(Ligne l: lignes){
            lesLignes.add(l);
        }
        lv_ligne.setItems(lesLignes);
    }
    public void setTypeChamp(String typeChamp) {            // déclaration du setter de TypeChamps
        this.typeChamp = typeChamp;
    }
    public void setNomChamp(String nomChamp) {
        this.nomChamp = nomChamp;
    }
    public void setNomColonne(String nomColonne) {
        this.nomColonne = nomColonne;
    }
}
