package sample;

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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ControllerMenu implements Initializable {
    @FXML private Button bt_gen_form;
    @FXML private Pane panel_menu;
    private Connexion connexion;
    private ArrayList<Base> listeBase;
    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle){
        panel_menu.setVisible(true);
        listeBase = new ArrayList<Base>();
        bt_gen_form.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                URL location = getClass().getResource("generationHtml.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                Parent root = null;
                try {
                    root = (Parent) fxmlLoader.load(location.openStream());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Stage popUp = new Stage();
                popUp.initModality(Modality.WINDOW_MODAL);
                popUp.initOwner(((Node) e.getSource()).getScene().getWindow());
                popUp.setScene(new Scene(root, 800, 600));
                popUp.show();
            }
        });
    }
    public void setListeBase(ArrayList<Base> liste) {
        this.listeBase = liste;
    }
    public void setC(Connexion c) {
        this.connexion = c;
    }
}

