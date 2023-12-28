package Vue;

import java.net.URL;

import Controleur.Ctrl_Client;
import Controleur.Ctrl_ServeurBanque;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServeurBanque extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			//CHARGEMENT DU FXML 
			URL fxmlURL=getClass().getResource("serveurbanque.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((Pane) root);
			
			
			//CHARGEMENT DU CONTROLEUR
			Ctrl_ServeurBanque ctrl = fxmlLoader.getController();
			ctrl.setPrimaryStage(primaryStage);
			//ctrl.chargement_listes_utilisateurs();
			
			
			//CHARGEMENT DE l'ICONE DE l'APPLICATION
			//Image icone = new Image("/icones/avance_client.PNG");
			
			//AFFECTATIONS DES PARAMETRES DE LA FENETRE
			primaryStage.setTitle("Projet RMI - Serveur Banque");
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED); // SA VEUT DIRE QU'ON ENLEVE LA FENETRE WINDOWS PAR DEFAUT  (fermer,agrandir,reduire)
			//primaryStage.getIcons().add(icone);
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
