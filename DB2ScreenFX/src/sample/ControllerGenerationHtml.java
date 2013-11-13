package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

public class ControllerGenerationHtml implements Initializable{
    @FXML private TableView<Base> liste_base;
    @FXML private TableColumn<Base, Integer> liste_base_col_num;
    @FXML private TableColumn<Base, String> liste_base_col_nom;

    @FXML private TableView<Table> liste_table;
    @FXML private TableColumn<Table, Integer> liste_table_col_num;
    @FXML private TableColumn<Table, String> liste_table_col_nom;

    @FXML private Button bt_generer;

    private ObservableList<Base> lesBasesObserve = FXCollections.observableArrayList();
    private ObservableList<Table> lesTablesObserves = FXCollections.observableArrayList();

    private ArrayList<Base> lesBases;
    private ArrayList<Table> lesTables;

    private Connexion connexion;


    public void initialize(URL url, ResourceBundle resourceBundle){
        liste_base_col_num.setCellValueFactory(new PropertyValueFactory<Base, Integer>("numBase"));
        liste_base_col_nom.setCellValueFactory(new PropertyValueFactory<Base, String>("nomBase"));

        liste_base.setItems(lesBasesObserve);

        liste_table_col_num.setCellValueFactory(new PropertyValueFactory<Table, Integer>("id"));
        liste_table_col_nom.setCellValueFactory(new PropertyValueFactory<Table, String>("nom"));
        liste_table.setItems(lesTablesObserves);

        liste_base.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                Base selected = (Base) newValue;
                lesTablesObserves.clear();
                if(lesTables != null){  //évite la répétition
                    lesTables.clear();
                }
                connexion.getLesTablesDuneBase(selected);
                lesTables = selected.getListeTable();

                for(Table t : lesTables){
                    lesTablesObserves.add(t);
                }


            }
        });

        bt_generer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                URL location = getClass().getResource("generationColonne.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load(location.openStream());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                ControllerAffichageColonne c = fxmlLoader.getController();
                Table t = liste_table.getSelectionModel().getSelectedItem();
                Base b = liste_base.getSelectionModel().getSelectedItem();
                try{
                    connexion.afficheNomColonnes(t, b);
                    c.setListeColonne(t.getListeColonne());
                    c.setConnexion(connexion);
                    c.setBase(b);
                    c.setTable(t);
                }catch (SQLException ex){
                    ex.printStackTrace();
                }

                c.setListeColonne(t.getListeColonne());

                Stage popUp = new Stage();
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(((Node) e.getSource()).getScene().getWindow());
                popUp.setScene(new Scene(root, 800, 600));
                popUp.show();
            }
        });
    }

    public void SetLesBases(ArrayList<Base> liste){
        this.lesBases = liste;
        for(Base b : this.lesBases){
            this.lesBasesObserve.add(b);
        }
    }

    public void setC(Connexion c) {
        this.connexion = c;
    }
}