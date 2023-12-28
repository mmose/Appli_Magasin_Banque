package Controleur;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Modele.Message;
import Modele.Produit;
import Modele.Utilisateur;
import RMI.Magasin.ServeurMagasin;
import RMI.Banque.ClientBanque;
import RMI.Magasin.ClientMagasin;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Ctrl_ClientBanqueMessage {


	@FXML
	private VBox VBox_client;
	@FXML
	private Text expediteur_message,date_message;
	@FXML
	private TextArea zone_message;
	@FXML
	private ListView<Produit> liste_achat = new ListView<>();

	private Stage stage;
	
	@FXML
	private AnchorPane parent;
	private double xOffset = 0;
	private double yOffset = 0;

	
	
	public void initData(Message m)
	{		
		stage = (Stage) date_message.getScene().getWindow();  
	
		expediteur_message.setText(m.getExpediteur());
		date_message.setText(m.getDate());
		zone_message.setText(m.getMessage());
		liste_achat.getItems().addAll(m.getPaniers());
	}
	

	public void rafraichir() throws ClassNotFoundException, IOException
	{
		
	}
	
	
	public void deplacer_fenetre()
	{
		parent.setOnMousePressed((event) -> {
		xOffset= event.getSceneX();
		yOffset=event.getSceneY();
		
		});
		parent.setOnMouseDragged((event) -> {
			stage.setX(event.getScreenX() - xOffset);
			stage.setY(event.getScreenY() - yOffset);
			stage.setOpacity(0.8f);
		});	
		parent.setOnDragDone((event) -> {
			stage.setOpacity(1.0f);
		});	
		parent.setOnMouseReleased((event) -> {
			stage.setOpacity(1.0f);
		});	
		
	}
	
	public void fermer() throws IOException 
	{
		System.out.println("Fermeture du message ");
		stage.close();
	}
	
	public void reduire()
	{
	 stage.setIconified(true);
	}
	
	public void setPrimaryStage(Stage s) {
		this.stage = s;
	}




	
	
}
