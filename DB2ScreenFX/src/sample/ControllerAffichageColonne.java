package sample;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ControllerAffichageColonne implements Initializable {
    @FXML private ComboBox cb_colonne;
    @FXML private TextField txt_nom_champ;
    @FXML private ComboBox cb_type_champ;
    @FXML private Button bt_enregistrer;
    @FXML private Button bt_creation_form;
    @FXML private TableView<Table> liste_enregistrement;
    @FXML private TableColumn<Enregistrement, String> tb_nom_colonne;
    @FXML private TableColumn<Enregistrement, String> tb_nom_champ;
    @FXML private TableColumn<Enregistrement, String> tb_type_champ;
    @FXML private TableColumn<Enregistrement, String> tb_ligne;

    private Connexion connexion;
    private Table table;
    private Base base;
    private Colonne colonne;
    private ArrayList<Colonne> listeColonne;
    private ObservableList lesEnregistrementObserves = FXCollections.observableArrayList();

    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle){
        tb_nom_colonne.setCellValueFactory(
                new PropertyValueFactory<Enregistrement, String>("nomColonne"));
        tb_nom_champ.setCellValueFactory(
                new PropertyValueFactory<Enregistrement, String>("nomChamp"));
        tb_type_champ.setCellValueFactory(
                new PropertyValueFactory<Enregistrement, String>("typeChamp"));
        tb_ligne.setCellValueFactory(
                new PropertyValueFactory<Enregistrement, String>("lesLignes"));
        liste_enregistrement.setItems(lesEnregistrementObserves);
        bt_enregistrer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String nomColonne = cb_colonne.getSelectionModel().getSelectedItem().toString(); //récupère nom de la colonne
                String nomChamp = txt_nom_champ.getText(); // récupère nom du champ
                String typeChamp = cb_type_champ.getSelectionModel().getSelectedItem().toString(); // récupère type du champ
                colonne = rechercheParNom(nomColonne);
                if(cb_type_champ.getSelectionModel().getSelectedIndex() == 2){ // si liste déroulante est sélectionnée
                    URL location = getClass().getResource("ajoutLigneColonne.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root = null;
                    try{
                        root = (Parent) fxmlLoader.load(location.openStream());
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    ControllerAjoutLigneColonne c = fxmlLoader.getController();
                    c.setNomChamp(nomChamp);
                    c.setNomColonne(nomColonne);
                    c.setTypeChamp(typeChamp);
                    try{
                        connexion.getLigneColonnes(table, base, colonne);
                        c.setLeslignes(colonne.getLesLignes());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    c.setConnexion(connexion);
                    Stage popUp = new Stage();
                    popUp.initModality(Modality.WINDOW_MODAL);
                    popUp.initOwner(((Node)e.getSource()).getScene().getWindow());
                    popUp.setScene(new Scene(root, 800, 600));
                    popUp.show();
                }else{
                    String ligne = "";
                    Enregistrement enregistrement = new Enregistrement(nomChamp, typeChamp, nomColonne, ligne);
                    lesEnregistrementObserves.add(enregistrement); //ajoute l'objet instancié si dessus
                    //ComboBox
                    int index = cb_colonne.getSelectionModel().getSelectedIndex();  // récupère l'index de l'élément sélectionné de la comboBox
                    cb_colonne.getItems().remove(index); // supprime l'élément sélectionné de la comboBox
                }
            }
        });
    }

    public void setLesEnregistrementObserves(Enregistrement e) {
        lesEnregistrementObserves.add(e);        System.out.println("--------"+e.getNomChamp());
        this.liste_enregistrement.setItems(lesEnregistrementObserves);
    }

    public void setListeColonne(ArrayList<Colonne> listeColonne) {
        this.listeColonne = listeColonne;
        for(Colonne c : listeColonne){
            String nom = c.getNomcolonne();
            cb_colonne.getItems().add(nom); //ajoute un nom dans la liste déroulante
        }
        cb_colonne.getSelectionModel().selectFirst(); //sélectionne par défaut le premier élément de la liste
    }
    public void setConnexion(Connexion c){
        this.connexion = c;
    }
    public void setTable(Table table) {
        this.table = table;
    }
    public void setBase(Base base) {
        this.base = base;
    }
    public Colonne rechercheParNom(String nom){
        boolean trouve = false;
        Colonne c = null;
        int i = 0;
        while((trouve == false)&&(i < this.listeColonne.size())){
            c = this.listeColonne.get(i);
            if (c.getNomcolonne() == nom){
                trouve = true;
            }
            i++;
        }
        if (trouve){
            return c;
        }else{
            return null;
        }
    }
}


