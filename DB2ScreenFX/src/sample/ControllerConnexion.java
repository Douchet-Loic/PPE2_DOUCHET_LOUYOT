package sample;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ControllerConnexion implements Initializable {
    @FXML private Button bt_connecter;
    @FXML private Button bt_quitter;
    @FXML private TextField field_text_id;
    @FXML private PasswordField field_password_mdp;
    @FXML private Text txt_erreur;
    private ArrayList<Base> listeBase;
    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle){
        listeBase = new ArrayList<Base>();
        bt_connecter.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent e){
                String id = field_text_id.getText();         //    récupère les valeurs saisis par l' utiisateur pour se connecter a la base de donnée
                String mdp = field_password_mdp.getText();
                Connexion c = new Connexion(id, mdp);
                Connection cnx = c.getConnection();

                if(cnx!=null){  // si l' identifiant est correcte alors
                    URL location = getClass().getResource("generationHtml.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(location);
                    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                    Parent root = null;
                    try{
                        root = (Parent) fxmlLoader.load(location.openStream());
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }

                    // erreur ici

                    c.chargerBase(); //récupère toutes les bases de données
                    listeBase = c.getListeBase(); // récupère la liste de la bases séléctionnée
                    // fin erreur
                    /*ControllerMenu menu = fxmlLoader.getController();
                    menu.setC(c);
                    menu.setListeBase(listeBase); */
                    Stage stage = (Stage) bt_quitter.getScene().getWindow();     // Le code du boutton quitter
                    stage.close();
                    Stage popUp = new Stage();
                    popUp.initModality(Modality.WINDOW_MODAL);
                    popUp.initOwner(((Node)e.getSource()).getScene().getWindow());
                    popUp.setScene(new Scene(root, 800, 600));
                    popUp.show();
                }else{     // si l' identifiant est faux alors
                    txt_erreur.setVisible(true);
                    System.out.println("Identifiant ou mot de passe incorrect");
                }
            }
        });
        bt_quitter.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent e){
                Stage stage = (Stage) bt_quitter.getScene().getWindow();
                stage.close();
            }
        });
    }
}

