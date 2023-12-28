package Vue;

import java.net.URL;

import Controleur.Ctrl_Client;
import Controleur.Ctrl_ClientBanque;
import Controleur.Ctrl_ClientMagasin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientBanque extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			//CHARGEMENT DU FXML 
			URL fxmlURL=getClass().getResource("clientbanque.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((Pane) root);
			
			//CHARGEMENT DU CONTROLEUR
			Ctrl_ClientBanque ctrl = fxmlLoader.getController();
			ctrl.setPrimaryStage(primaryStage);

			//AFFECTATIONS DES PARAMETRES DE LA FENETRE
			primaryStage.setTitle("Projet RMI - Client");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED); // SA VEUT DIRE QU'ON ENLEVE LA FENETRE WINDOWS PAR DEFAUT  (fermer,agrandir,reduire)
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	private static void main(String[] args) {
		launch(args);
	}



}
