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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Ctrl_ClientBanque {


	@FXML
	private VBox VBox_client;
	@FXML
	private Text solde,txt_client,txt_port,txt_banque;
	@FXML
	private ListView<Message> liste_messages = new ListView<>();

	private Stage stage;
	
	@FXML
	private AnchorPane parent;
	private double xOffset = 0;
	private double yOffset = 0;

	ClientBanque nouveauclient;
	Utilisateur serveurbanque;
	
	
	public void initData(Utilisateur clientBanque, ClientBanque clientbanque, Utilisateur serveurbanque)
	{		
		nouveauclient = clientbanque;
		this.serveurbanque=serveurbanque;
		
		stage = (Stage) txt_client.getScene().getWindow();
		txt_banque.setText(serveurbanque.getUsername());
		txt_port.setText(Integer.toString(serveurbanque.getPort_user()));
		txt_client.setText(clientBanque.getUsername());
		GetSolde();
		Ecriture_Client_Connecté(clientBanque, serveurbanque);

	}
	
	public void GetSolde()
	{
		solde.setText(Double.toString(nouveauclient.Consulter_Solde()));
	}
	
	public void GetMessage()
	{
		liste_messages.getItems().addAll(nouveauclient.Consulter_Message());
	}
	
	public void Double_Click_Message()
	{
		liste_messages.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		    	Message message = new Message(null, null, null, null, 0, null);
		        if (click.getClickCount() == 2) {
		        	message = liste_messages.getSelectionModel().getSelectedItem();
		        	
		        	// OPEN NEW VIEW OF THE MESSAGE
		        	FXMLLoader loader = new FXMLLoader(
		        		    getClass().getResource(
		        		      "/Vue/messagebanque.fxml"
		        		    )
		        		  );

		        		  Stage stage = new Stage(StageStyle.DECORATED);
		        		  try {
							stage.setScene(
							    new Scene(loader.load())
							  );
						} catch (IOException e) {
							e.printStackTrace();
						}

		        		  Ctrl_ClientBanqueMessage controller = loader.getController();
		        		  controller.initData(message);
		        		  stage.initStyle(StageStyle.UNDECORATED);
		        		  stage.show();
		        		  
		        }
		    }
		});
	}
	
	public void Ecriture_Client_Connecté(Utilisateur user, Utilisateur banque)
	{
		try (FileWriter f = new FileWriter("client_banque.tmp", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);) 
	 	{
            	p.println(user.toString3()+"|"+banque.getUsername()+"|"+banque.getPort_user());
        } catch (IOException i) {
            i.printStackTrace();
        }      
	}
	
	public void rafraichir() throws ClassNotFoundException, IOException
	{
		GetSolde();
		liste_messages.getItems().clear();
		GetMessage();
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
		suppresion_ligne_serveur();
		System.out.println("Fermeture du client magasin ");
		System.exit(0);	
	}
	
	public void suppresion_ligne_serveur() throws IOException
	{
        RandomAccessFile file = new RandomAccessFile("client_banque.tmp", "rw");
        String delete;
        String task="";
        
        while ((delete = file.readLine()) != null) {
            if (delete.startsWith(nouveauclient.getClientname())) {
                continue;
            } 
            task+=delete+"\n";
        }
 
        System.out.println("Supression de la ligne : "+nouveauclient.getClientname()+" dans le fichier tmp");
        BufferedWriter writer = new BufferedWriter(new FileWriter("client_banque.tmp"));
        writer.write(task);
        file.close();
        writer.close();
	}
	
	public void reduire()
	{
	 stage.setIconified(true);
	}
	
	public void setPrimaryStage(Stage s) {
		this.stage = s;
	}




	
	
}
